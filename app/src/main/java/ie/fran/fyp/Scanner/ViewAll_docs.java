package ie.fran.fyp.Scanner;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ie.fran.fyp.R;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class ViewAll_docs extends Fragment {
    public static final String EXTRA_MESSAGE = "com.example.camera.MESSAGE";
    ImageAdapter imageAdapter;
    File[] files;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_gall, container, false);


        GridView gridview = (GridView) view.findViewById(R.id.grid_view);
        imageAdapter = new ImageAdapter(getActivity());
        gridview.setAdapter(imageAdapter);
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        files = storageDir.listFiles();
        for (File file : files){
            imageAdapter.add(file.getAbsolutePath());
        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {


                Intent intent = new Intent(getActivity(), ViewImageActivity.class);
                String message = files[position].getAbsolutePath();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);

            }
        });

        return view;
    }
}

