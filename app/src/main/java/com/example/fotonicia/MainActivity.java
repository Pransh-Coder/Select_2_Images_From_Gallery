package com.example.fotonicia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    ImageView imageView1,imageView2;
    Button button,selectImgFromGallery;
    private final int CODE_MULTIPLE_IMG_GALLERY=2;
    private int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = findViewById(R.id.imageview1);
        imageView2 = findViewById(R.id.imageView2);
        button = findViewById(R.id.btn);
        selectImgFromGallery = findViewById(R.id.select);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=0;
                OverLayImage(view);
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag =5;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra("flag value",flag);
                startActivityForResult(Intent.createChooser(intent,"Select pics"),1);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=3;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra("flag value",flag);
                startActivityForResult(Intent.createChooser(intent,"Select pics"),1);
            }
        });

        selectImgFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select pics"),CODE_MULTIPLE_IMG_GALLERY);
                Toast.makeText(MainActivity.this, "Select 2 Images directly! ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void OverLayImage(View view) {
        imageView1.setImageDrawable(getResources().getDrawable(R.drawable.element1));
        imageView2.setImageDrawable(getResources().getDrawable(R.drawable.element2));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1&& data!=null&&data.getData()!=null){
            Uri uri  = data.getData();
            if (flag==5){
                imageView1.setImageURI(uri);
                imageView1.setOnTouchListener(new ImageMatrixTouchHandler(MainActivity.this));
            }
            else if(flag==3){
                imageView2.setImageURI(uri);
                imageView2.setOnTouchListener(new ImageMatrixTouchHandler(MainActivity.this));
            }
            //imageView1.setImageURI(uri);
            //imageView2.setImageURI(uri);
            //imageView1.setOnTouchListener(new ImageMatrixTouchHandler(MainActivity.this));
        }
        else if(resultCode == RESULT_OK && requestCode == CODE_MULTIPLE_IMG_GALLERY){

            ClipData clipData =data.getClipData();

            if (clipData!=null){
                imageView1.setImageURI(clipData.getItemAt(0).getUri());
                imageView2.setImageURI(clipData.getItemAt(1).getUri());

                imageView1.setOnTouchListener(new ImageMatrixTouchHandler(MainActivity.this));
                imageView2.setOnTouchListener(new ImageMatrixTouchHandler(MainActivity.this));


                //for getting path for multiple images  [this loop is used for multiple images and by selecting those images we could gets its paths]
                for(int i=0;i<clipData.getItemCount();i++){

                    ClipData.Item item = clipData.getItemAt(i);         //Description of a single item in a ClipData. which can be of form string uri
                    Uri uri = item.getUri();
                    Log.e("URI's : ",uri.toString());


                }
            }

        }
    }
}