package com.example.prjhpbd;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.ScaleGestureDetector;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {
    private VideoView videoView;
    private TextureView textureView;
    ImageView imgbdd;
    Button bt2;
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;

    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        imgbdd=findViewById(R.id.imgbdd);
        bt2=findViewById(R.id.bt2);
        // Khởi tạo videoView và thiết lập video
        videoView = findViewById(R.id.videoView);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(MainActivity3.this,MainActivity4.class);
                startActivity(myintent);
            }
        });
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videohpbd));
        videoView.start();
        // Khởi tạo TextureView và ScaleGestureDetector
        textureView = findViewById(R.id.textureView);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        // Đặt trình nghe sự kiện cảm ứng cho TextureView
        textureView.setOnTouchListener((v, event) -> {
            scaleGestureDetector.onTouchEvent(event);
            return true;
        });
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Ở chế độ ngang, chỉ hiển thị VideoView
            textureView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            // Loại bỏ MediaController để video có thể phát toàn màn hình
            videoView.setMediaController(null);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Ở chế độ dọc, chỉ hiển thị TextureView
            videoView.setVisibility(View.GONE);
            textureView.setVisibility(View.VISIBLE);
            // Thiết lập lại MediaController cho VideoView
            videoView.setMediaController(new MediaController(this));
        }
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 10.0f));
            textureView.setScaleX(scaleFactor);
            return true;
        }
    }
}
