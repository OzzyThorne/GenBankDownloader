package org.ozzy.genebank.downloader;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.editText);
        final EditText fileName = findViewById(R.id.fileName);
        final EditText speciesName = findViewById(R.id.speciesName);
        context =getApplicationContext();
        createFolder();
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                0);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                0);

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Log.i("0", "onClick: "+ editText.getText().toString());
                            new GenBank(editText.getText().toString(),fileName.getText().toString(),speciesName.getText().toString());
                        } catch (IOException e) {

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
    public static Context getContext() {
        return context;
    }
    private void createFolder() {
        //新建一个File，传入文件夹目录
        File file = new File("/mnt/sdcard/Documents/Genbank");
        //判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!file.exists()) {
            //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
            file.mkdirs();
        }
    }
    public static void toastShower(String string){
        Toast.makeText(MainActivity.getContext(),string,Toast.LENGTH_LONG).show();
    }
}
