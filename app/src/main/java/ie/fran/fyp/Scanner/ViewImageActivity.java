package ie.fran.fyp.Scanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import ie.fran.fyp.R;

public class ViewImageActivity extends AppCompatActivity {
Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        delete = findViewById(R.id.delete);

        Intent intent = getIntent();
        final String message = intent.getStringExtra(ViewAll_docs.EXTRA_MESSAGE);

        File imgFile = new  File(message);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(message);

            ImageView myImage = (ImageView) findViewById(R.id.imageView);

            myImage.setImageBitmap(myBitmap);

        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File fdelete = new File(message);
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
                        Toast.makeText(ViewImageActivity.this,"image delete",  Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                    }
                }
            }
        });
    }
}
