package com.example.shisjin.designwindow;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shisjin.designwindow.srp.ImageLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView loaderImage;
    private ImageView imgeview;
    private ImageLoader imageLoader;
    private String imageUrl;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    imgeview.setImageBitmap(bitmap);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        imageUrl="http://img4.imgtn.bdimg.com/it/u=1489829557,2319829425&fm=27&gp=0.jpg";
    }

    private void initView() {
        loaderImage = (TextView) findViewById(R.id.loaderImage);
        imgeview = (ImageView) findViewById(R.id.imgeview);
        imageLoader= new ImageLoader();
        loaderImage.setOnClickListener(this);
        imageLoader.setIsUseDiskCahce(true);
        imageLoader.setIsUseDoubleCahec(true);
        imageLoader.setlisetnerLoad(new ImageLoader.SetDisplayImage() {
            @Override
            public void setImageView(Bitmap bitmap) {
                //imgeview.setImageBitmap(bitmap);
                Message msg = Message.obtain();
                msg.obj=bitmap;
                msg.what=1;
                handler.sendMessage(msg);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loaderImage:
                imageLoader.dispalyImage(imageUrl,imgeview);
                break;
            default:
                break;
        }
    }
}
