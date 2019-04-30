package ie.fran.fyp.Scanner;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import ie.fran.fyp.R;

public class Scan_New_doc extends Fragment {

    private static final String TAG = "CapturePicture";
    static final int REQUEST_PICTURE_CAPTURE = 2;
    private ImageView image;
    private String pictureFilePath;
    private FirebaseStorage firebaseStorage;
    private String deviceIdentifier;
    private ProgressDialog mDialog;
    FirebaseAuth fAuth;
    private EditText mPostTitle;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    Button captureButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_capture_picture);
        View view = inflater.inflate(R.layout.activity_capture_picture, container, false);

        image = view.findViewById(R.id.picture);
        mDialog = new ProgressDialog(getActivity());
        captureButton = view.findViewById(R.id.capture);

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        return;
                    }
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getActivity(),"ie.fran.fyp.android.fileprovider",photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                }
            }
        });
        return view;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        }
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg", storageDir);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            Snackbar.make(getView(),"Copy saved", Snackbar.LENGTH_SHORT).show();

            File imgFile = new  File(mCurrentPhotoPath);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                image.setImageBitmap(myBitmap);
               // ImageView myImage = (ImageView) findViewById(R.id.imageView);
               // myImage.setImageBitmap(myBitmap);
            }
        }
    }
}

//    private View.OnClickListener capture = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            if(getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
//                sendTakePictureIntent();
//            }
//        }
//    };
//    private void sendTakePictureIntent() {
//
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra( MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
//        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);
//
//            File pictureFile = null;
//            try {
//                pictureFile = getPictureFile();
//            } catch (IOException ex) {
//                Toast.makeText(getActivity(),
//                        "Photo file can't be created, please try again",
//                        Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (pictureFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(getActivity(),
//                        "ie.fran.fyp.android.fileprovider",
//                        pictureFile);
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);
//            }
//        }
//    }
//    private File getPictureFile() throws IOException {
//        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//        String pictureFile = "StudyAid" + timeStamp;
//        File storageDir =getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(pictureFile,  ".jpg", storageDir);
//        pictureFilePath = image.getAbsolutePath();
//        return image;
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_PICTURE_CAPTURE && resultCode == RESULT_OK) {
//            File imgFile = new  File(pictureFilePath);
//            if(imgFile.exists())            {
//                image.setImageURI(Uri.fromFile(imgFile));
//            }
//        }
//    }
//    //save captured picture in gallery
//    private View.OnClickListener saveGallery = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            addToGallery();
//        }
//    };
//    private void addToGallery() {
//        Intent galleryIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(pictureFilePath);
//        Uri picUri = Uri.fromFile(f);
//        galleryIntent.setData(picUri);
//        getActivity().sendBroadcast(galleryIntent);
//    }
//    //save captured picture on cloud storage
//    private View.OnClickListener saveCloud = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            mDialog.setMessage("Uploading Image from image...");
//            mDialog.show();
//            addToCloudStorage();
//        }
//    };
//    private void addToCloudStorage() {
//        File f = new File(pictureFilePath);
//        final Uri picUri = Uri.fromFile(f);
//        final String cloudFilePath = deviceIdentifier + picUri.getLastPathSegment();
//        fAuth = FirebaseAuth.getInstance();
//
//        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
//        final StorageReference storageRef = firebaseStorage.getReference();
//        final StorageReference uploadeRef = storageRef.child("photo").child(fAuth.getCurrentUser().getUid()).child(cloudFilePath);
//
//        UploadTask uploadTask = uploadeRef.putFile(picUri);
//        uploadTask.addOnFailureListener(getActivity(), new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e(TAG,"Failed to upload picture to cloud storage");
//            }
//        }).addOnSuccessListener(getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
//                while(!uri.isComplete());
//                Uri url = uri.getResult();
//
//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
//                        .child("uploads")
//                        .child(fAuth.getCurrentUser().getUid());
//                Map updateMap = new HashMap();
//                updateMap.put("img", url.toString());
//                updateMap.put("ten", "fran");
//
//                ref.setValue(updateMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        mDialog.dismiss();
//                        Toast.makeText(getActivity(),
//                                "Image has been uploaded to cloud storage",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e(TAG,"Failed to upload picture to cloud storage");
//
//                    }
//                });
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                mDialog.setMessage("Uploaded"+(int)progress+"%");
//            }
//        });
////        uploadeRef.putFile(picUri).addOnFailureListener(new OnFailureListener(){
////            public void onFailure(@NonNull Exception exception){
////                Log.e(TAG,"Failed to upload picture to cloud storage");
////            }
////        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
////            @Override
////            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
////
////                mDialog.dismiss();
////                DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
////                        .child("uploads")
////                        .child(fAuth.getCurrentUser().getUid());
////                String jav = taskSnapshot.getMetadata().toString();
////                ref.child("img").setValue(jav);
////                Toast.makeText(getActivity(),
////                        "Image has been uploaded to cloud storage",
////                        Toast.LENGTH_SHORT).show();
////
////            }
////        }).addOnFailureListener(new OnFailureListener() {
////            @Override
////            public void onFailure(@NonNull Exception e) {
////                Toast.makeText(getActivity(),
////                        "Error",
////                        Toast.LENGTH_SHORT).show();
////
////            }
////        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
////            @Override
////            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
////                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
////                mDialog.setMessage("Uploaded"+(int)progress+"%");
////            }
////        });
//    }
//    protected synchronized String getInstallationIdentifier() {
//        if (deviceIdentifier == null) {
//            SharedPreferences sharedPrefs = getActivity().getSharedPreferences(
//                    "DEVICE_ID", Context.MODE_PRIVATE);
//            deviceIdentifier = sharedPrefs.getString("DEVICE_ID", null);
//            if (deviceIdentifier == null) {
//                deviceIdentifier = UUID.randomUUID().toString();
//                SharedPreferences.Editor editor = sharedPrefs.edit();
//                editor.putString("DEVICE_ID", deviceIdentifier);
//                editor.commit();
//            }
//        }
//        return deviceIdentifier;
//    }
//}
