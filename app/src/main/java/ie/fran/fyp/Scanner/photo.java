package ie.fran.fyp.Scanner;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;

import ie.fran.fyp.Dashboard;
import ie.fran.fyp.R;
import ie.fran.fyp.ToDo.ToDo_Activity;
public class photo extends AppCompatActivity {

    private Button mUploadImage, save ;

    private ImageView mImageView;

    private static final int CAPTURE_IMAGE=2;

    private StorageReference mStorage;

    private ProgressDialog mDialog;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        fAuth = FirebaseAuth.getInstance();
        mStorage=FirebaseStorage.getInstance().getReference("Photos");


        mDialog=new ProgressDialog(this);

        mUploadImage=(Button)findViewById(R.id.add_image_capture_camera);
        mImageView=(ImageView)findViewById(R.id.image_show_xml);

        mUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAPTURE_IMAGE);


            }
        });
        save =(Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    private void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("photo")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("imageUrl");
        ref.setValue(imageEncoded);
        mDialog.dismiss();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== CAPTURE_IMAGE  && resultCode == RESULT_OK) {

                mDialog.setMessage("Uploading Image from capture image...");
                mDialog.show();
                Uri uri = data.getData();
                if (data != null) {
//                    Picasso.get().load(uri).fit().centerCrop().into(mImageView);
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    mImageView.setImageBitmap(photo);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                    DatabaseReference ref = FirebaseDatabase.getInstance()
                            .getReference()
                            .child("photo")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("imageUrl");
                    ref.setValue(imageEncoded);
                    mDialog.dismiss();

                }

//                StorageReference filePath = mStorage.child(fAuth.getCurrentUser().getUid()).child("Photos").child(uri.getLastPathSegment());
//                mStorage  = mStorage.child(fAuth.getCurrentUser().getUid()).child("Photos").child(uri.getLastPathSegment());
//            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                    Uri downloadUri=taskSnapshot.getDownloadUrl();
//
//                    Picasso.with(MainActivity.this).load(downloadUri).fit().centerCrop().into(mImageView);
//
//
//                    Toast.makeText(getApplicationContext(),"Upload Successfully",Toast.LENGTH_SHORT).show();
//
//                    mDialog.dismiss();
//
//                }
//            });
//                mStorage.putFile(uri).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(), "Upload failed", Toast.LENGTH_SHORT).show();
//
//                    }
//                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                        Picasso.with(photo.this).load(String.valueOf(taskSnapshot)).fit().centerCrop().into(mImageView);
//                        Picasso.get().load(uri).into(mImageView);
//                        Toast.makeText(getApplicationContext(), "Upload Successfully", Toast.LENGTH_SHORT).show();
//                        mDialog.dismiss();
//
//                    }
//                });
//
//
        }

    }



}
