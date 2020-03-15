package tw.org.iii.ellen.ellen10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private File sdroot, approot ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Here, thisActivity is the current activity
        // 檢查是否有權限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                //沒有權限 => 去詢問
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        123);

        } else {
            // Permission has already been granted
            Log.v("ellen","debug1") ;
            init() ;
        }

    }

    // 詢問授權
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.v("ellen","debug2") ;
                init() ;

            }else{
                Log.v("ellen","debug3") ;
                finish() ;
            }
        }

    }

    private void init(){
        String state = Environment.getExternalStorageState() ;
        Log.v("ellen",state) ;  // mounted掛載 or removed移除
        sdroot = Environment.getExternalStorageDirectory() ;
        Log.v("ellen",sdroot.getAbsolutePath()) ;
        approot = new File(sdroot, "Android/data/"+getPackageName()) ;
        if (! approot.exists()){
            approot.mkdirs() ;
        }
    }

    public void test1(View view) {
        try {
            FileOutputStream fout = new FileOutputStream(
                    sdroot.getAbsoluteFile()+"/000.txt");
            fout.write("Hello, World".getBytes()) ;
            fout.flush() ;
            fout.close() ;
            Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show() ;
        }catch (Exception e){
            Log.v("ellen",e.toString()) ;
        }
    }

    public void test2(View view) {
        try {
            FileOutputStream fout = new FileOutputStream(
                    approot.getAbsoluteFile()+"/000.txt");
            fout.write("Hello, World2".getBytes()) ;
            fout.flush() ;
            fout.close() ;
            Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show() ;
        }catch (Exception e){
            Log.v("ellen",e.toString()) ;
        }

    }

    public void test3(View view) {
        try{
            FileInputStream fin = new FileInputStream(
                    sdroot.getAbsoluteFile()+"/000.txt") ;
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin)) ;
            String line = reader.readLine() ;
            fin.close();
            Log.v("ellen",line) ;

        }catch (Exception e){
            Log.v("ellen",e.toString()) ;
        }
    }

    public void test4(View view) {
        try{
            FileInputStream fin = new FileInputStream(
                    approot.getAbsoluteFile()+"/000.txt") ;
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin)) ;
            String line = reader.readLine() ;
            fin.close();
            Log.v("ellen",line) ;

        }catch (Exception e){
            Log.v("ellen",e.toString()) ;
        }
    }
}
