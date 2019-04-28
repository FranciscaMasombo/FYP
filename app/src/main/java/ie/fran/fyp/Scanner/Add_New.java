package ie.fran.fyp.Scanner;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import ie.fran.fyp.R;
import android.support.v4.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class Add_New extends Fragment {

    private static final String TAG = "CapturePicture";
    static final int REQUEST_PICTURE_CAPTURE = 2;
    private ImageView image;
    private String pictureFilePath;
    private FirebaseStorage firebaseStorage;
    private String deviceIdentifier;
    private ProgressDialog mDialog;
    FirebaseAuth fAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_capture_picture);
        View view = inflater.inflate(R.layout.activity_capture_picture, container, false);

        image = view.findViewById(R.id.picture);
        mDialog=new ProgressDialog(getActivity());

        Button captureButton = view.findViewById(R.id.capture);
        captureButton.setOnClickListener(capture);
        if(!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            captureButton.setEnabled(false);
        }

        view.findViewById(R.id.save_local).setOnClickListener(saveGallery);
        view.findViewById(R.id.save_cloud).setOnClickListener(saveCloud);

        firebaseStorage = FirebaseStorage.getInstance();
        getInstallationIdentifier();
        return view;
    }

    private View.OnClickListener capture = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                sendTakePictureIntent();
            }
        }
    };
    private void sendTakePictureIntent() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra( MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);

            File pictureFile = null;
            try {
                pictureFile = getPictureFile();
            } catch (IOException ex) {
                Toast.makeText(getActivity(),
                        "Photo file can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (pictureFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "ie.fran.fyp.android.fileprovider",
                        pictureFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);
            }
        }
    }
    private File getPictureFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pictureFile = "StudyAid" + timeStamp;
        File storageDir =getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(pictureFile,  ".jpg", storageDir);
        pictureFilePath = image.getAbsolutePath();
        return image;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICTURE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new  File(pictureFilePath);
            if(imgFile.exists())            {
                image.setImageURI(Uri.fromFile(imgFile));
            }
        }
    }
    //save captured picture in gallery
    private View.OnClickListener saveGallery = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addToGallery();
        }
    };
    private void addToGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(pictureFilePath);
        Uri picUri = Uri.fromFile(f);
        galleryIntent.setData(picUri);
        getActivity().sendBroadcast(galleryIntent);
    }
    //save captured picture on cloud storage
    private View.OnClickListener saveCloud = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mDialog.setMessage("Uploading Image from image...");
            mDialog.show();
            addToCloudStorage();
        }
    };
    private void addToCloudStorage() {
        File f = new File(pictureFilePath);
        Uri picUri = Uri.fromFile(f);
        final String cloudFilePath = deviceIdentifier + picUri.getLastPathSegment();
        fAuth = FirebaseAuth.getInstance();

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = firebaseStorage.getReference();
        StorageReference uploadeRef = storageRef.child(fAuth.getCurrentUser().getUid()).child(cloudFilePath);

        uploadeRef.putFile(picUri).addOnFailureListener(new OnFailureListener(){
            public void onFailure(@NonNull Exception exception){
                Log.e(TAG,"Failed to upload picture to cloud storage");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                mDialog.dismiss();

                Toast.makeText(getActivity(),
                        "Image has been uploaded to cloud storage",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }
    protected synchronized String getInstallationIdentifier() {
        if (deviceIdentifier == null) {
            SharedPreferences sharedPrefs = getActivity().getSharedPreferences(
                    "DEVICE_ID", Context.MODE_PRIVATE);
            deviceIdentifier = sharedPrefs.getString("DEVICE_ID", null);
            if (deviceIdentifier == null) {
                deviceIdentifier = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("DEVICE_ID", deviceIdentifier);
                editor.commit();
            }
        }
        return deviceIdentifier;
    }
}
