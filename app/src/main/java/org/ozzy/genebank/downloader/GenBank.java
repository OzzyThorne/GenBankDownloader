package org.ozzy.genebank.downloader;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;
import static java.lang.Math.min;

public class GenBank {
private String mFileName;
private File saveFile;
    public GenBank(String accession, String fileName, String speciesName) throws IOException, InterruptedException, JSONException {
        mFileName=fileName;
        Toast toast;
        crFile(fileName);
        String totalData;
        List<String> accessionList= Arrays.asList(accession.split("\\s+"));//以空格为界将字符串分隔开
        List<String> speciesList= Arrays.asList(speciesName.split(";"));
            for(int i=0;i<min(accessionList.size(),speciesList.size());i++){
                String Url = "https://www.ncbi.nlm.nih.gov/nuccore/"+ accessionList.get(i) + ".1?report=fasta&log$=seqview&format=text";
                String responseData = (new Downloader(Url)).getData();
                String uid = (responseData.split("\"ncbi_uidlist\" content=\"")[1]).split("\"")[0];
                String Url2 = "https://www.ncbi.nlm.nih.gov/sviewer/viewer.fcgi?id=" + uid +"&db=nuccore&report=fasta&retmode=text&withmarkup=on&tool=portal&log$=seqview&maxdownloadsize=1000000";
                String sequence = (new Downloader(Url2)).getData();
                List<String> sequenceList = Arrays.asList(sequence.split("\n"));
                sequence = ">" + speciesList.get(i);
                for(int j=1;j<sequenceList.size();j++){
                    sequence = sequence +"\n" + sequenceList.get(j);
                }
                sequence = sequence + "\n\n";
                writeData(sequence);
                //MainActivity.toastShower("Downloaded "+i+", Total "+ accessionList.size() +".");
                Log.i(TAG, "GenBank: Downloaded "+i+", Total "+ accessionList.size() +".");

            }
            //MainActivity.toastShower("Download Complete");

    }


    // 创建文件 写入文件内容
    private void crFile(String name) throws FileNotFoundException {
        saveFile = new File("/mnt/sdcard/Documents/Genbank", name + ".txt");
    }
    private void writeData(String data) throws IOException {
        FileOutputStream fos2 = new FileOutputStream(saveFile, true);// 第二個参数为true表示程序每次运行都是追加字符串在原有的字符上


            fos2.write(data.getBytes());
            fos2.write("\r\n".getBytes());// 写入一个换行

        // 释放资源
        fos2.close();
    }
}
