package com.indomi.disgrafia;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class activity_input_anak2 extends AppCompatActivity {
    private TextView umur,namaAnak;
    private ImageView kiri,kanan,atas,pegang,tulisan;
    private Button result;
    private ProgressDialog loadingBar;
    private static final int GalleryPick =1;
    private Uri imageUriKiri,imageUriKanan,imageUriAtas,imageUriPegang,imageUriTulisan;
    private StorageReference gambarRefKiri,gambarRefKanan,gambarRefAtas,gambarRefPegang,gambarRefTulisan;
    private DatabaseReference ref;
    private String namaAnakUser,umurAnak,saveCurrentDate,saveCurrentTime, dataRandomKey
            ,downloadImageUrlKiri,downloadImageUrlKanan,downloadImageUrlAtas,downloadImageUrlPegang,downloadImageUrlTulisan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_anak2);
        loadingBar = new ProgressDialog(this);
        namaAnak = (TextView) findViewById(R.id.nama_anak);
        umur = (TextView) findViewById(R.id.umur_anak);
        kiri = (ImageView) findViewById(R.id.gambar_kiri);
        kanan = (ImageView) findViewById(R.id.gambar_kanan);
        atas = (ImageView) findViewById(R.id.gambar_atas);
        pegang = (ImageView) findViewById(R.id.gambar_pegang);
        tulisan = (ImageView) findViewById(R.id.gambar_tulisan);
        result = (Button) findViewById(R.id.tombol_input_anak);

        gambarRefKiri = FirebaseStorage.getInstance().getReference().child("Gambar_Kiri");
        gambarRefKanan = FirebaseStorage.getInstance().getReference().child("Gambar_Kanan");
        gambarRefAtas = FirebaseStorage.getInstance().getReference().child("Gambar_Atas");
        gambarRefPegang = FirebaseStorage.getInstance().getReference().child("Gambar_Pegang");
        gambarRefTulisan = FirebaseStorage.getInstance().getReference().child("Gambar_Tulisan");

        ref = FirebaseDatabase.getInstance().getReference().child("Anak");

        kiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGalleryKiri();
            }
        });
        kanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGalleryKanan();
            }
        });
        atas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGalleryAtas();
            }
        });
        pegang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGalleryPegang();
            }
        });
        tulisan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGalleryTulisan();
            }
        });
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiData();
            }
        });
    }

    private void validasiData() {
        namaAnakUser = namaAnak.getText().toString();
        umurAnak = umur.getText().toString();

        if (TextUtils.isEmpty(namaAnakUser)){
            Toast.makeText(this,"Nama Anak Anda Belum Dimasukkan!",Toast.LENGTH_SHORT);
        }
        else if (TextUtils.isEmpty(umurAnak)){
            Toast.makeText(this,"Umur Anak Anda Belum Dimasukkan!",Toast.LENGTH_SHORT);
        }
        else if (imageUriKiri == null ){
            Toast.makeText(this,"Tidak ada gambar tampak kiri",Toast.LENGTH_SHORT);
        }
        else if (imageUriKanan == null ){
            Toast.makeText(this,"Tidak ada gambar tampak kanan",Toast.LENGTH_SHORT);
        }
        else if (imageUriAtas == null ){
            Toast.makeText(this,"Tidak ada gambar tampak atas",Toast.LENGTH_SHORT);
        }
        else if (imageUriPegang == null ){
            Toast.makeText(this,"Tidak ada gambar tampak pegang",Toast.LENGTH_SHORT);
        }
        else if (imageUriTulisan == null ){
            Toast.makeText(this,"Tidak ada gambar tampak tulisan",Toast.LENGTH_SHORT);
        }
        else simpanData();

    }

    private void simpanData() {
        loadingBar.setTitle("Menambahkan Data Anak Anda");
        loadingBar.setMessage("Mohon menunggu, sedang dilakukan proses penambahan data .....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar kalender = Calendar.getInstance();

        SimpleDateFormat tanggal = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = tanggal.format(kalender.getTime());

        SimpleDateFormat jam = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = jam.format(kalender.getTime());

        dataRandomKey = saveCurrentDate +(" ") + saveCurrentTime;
        final StorageReference filePathKiri = gambarRefKiri.child(imageUriKiri.getLastPathSegment() + dataRandomKey + "jpg");
        final StorageReference filePathKanan = gambarRefKanan.child(imageUriKanan.getLastPathSegment() + dataRandomKey + "jpg");
        final StorageReference filePathAtas = gambarRefAtas.child(imageUriAtas.getLastPathSegment() + dataRandomKey + "jpg");
        final StorageReference filePathPegang = gambarRefPegang.child(imageUriPegang.getLastPathSegment() + dataRandomKey + "jpg");
        final StorageReference filePathTulisan = gambarRefTulisan.child(imageUriTulisan.getLastPathSegment() + dataRandomKey + "jpg");

        final UploadTask aplotKiri = filePathKiri.putFile(imageUriKiri);
        final UploadTask aplotKanan = filePathKanan.putFile(imageUriKanan);
        final UploadTask aplotAtas = filePathAtas.putFile(imageUriAtas);
        final UploadTask aplotPegang = filePathPegang.putFile(imageUriPegang);
        final UploadTask aplotTulisan = filePathTulisan.putFile(imageUriTulisan);

        aplotKiri.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String pesan = e.toString();
                Toast.makeText(activity_input_anak2.this, "Pesan Error : "+pesan, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(activity_input_anak2.this, "Upload Gambar Tamnpak Kiri Berhasil", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = aplotKiri.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        downloadImageUrlKiri = filePathKiri.getDownloadUrl().toString();
                        return filePathKiri.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrlKiri = task.getResult().toString();
                            Toast.makeText(activity_input_anak2.this, "Sukses Menerima Image Uri", Toast.LENGTH_SHORT).show();

                            SaveDataInfoToDatabase();
                        }
                    }
                });
            }
        });

        aplotKanan.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String pesan = e.toString();
                Toast.makeText(activity_input_anak2.this, "Pesan Error : "+pesan, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(activity_input_anak2.this, "Upload Gambar Tamnpak Kanan Berhasil", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = aplotKanan.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        downloadImageUrlKanan = filePathKanan.getDownloadUrl().toString();
                        return filePathKanan.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrlKanan = task.getResult().toString();
                            Toast.makeText(activity_input_anak2.this, "Sukses Menerima Image Uri", Toast.LENGTH_SHORT).show();

                            SaveDataInfoToDatabase();
                        }
                    }
                });
            }
        });

        aplotAtas.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String pesan = e.toString();
                Toast.makeText(activity_input_anak2.this, "Pesan Error : "+pesan, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(activity_input_anak2.this, "Upload Gambar Tamnpak Atas Berhasil", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = aplotAtas.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        downloadImageUrlAtas = filePathAtas.getDownloadUrl().toString();
                        return filePathAtas.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrlAtas = task.getResult().toString();
                            Toast.makeText(activity_input_anak2.this, "Sukses Menerima Image Uri", Toast.LENGTH_SHORT).show();

                            SaveDataInfoToDatabase();
                        }
                    }
                });
            }
        });

        aplotPegang.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String pesan = e.toString();
                Toast.makeText(activity_input_anak2.this, "Pesan Error : "+pesan, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(activity_input_anak2.this, "Upload Gambar Tamnpak Pegang Berhasil", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = aplotPegang.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        downloadImageUrlPegang = filePathPegang.getDownloadUrl().toString();
                        return filePathPegang.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrlPegang = task.getResult().toString();
                            Toast.makeText(activity_input_anak2.this, "Sukses Menerima Image Uri", Toast.LENGTH_SHORT).show();

                            SaveDataInfoToDatabase();
                        }
                    }
                });
            }
        });

        aplotKiri.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String pesan = e.toString();
                Toast.makeText(activity_input_anak2.this, "Pesan Error : "+pesan, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(activity_input_anak2.this, "Upload Gambar Tamnpak Kiri Berhasil", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = aplotKiri.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        downloadImageUrlKiri = filePathKiri.getDownloadUrl().toString();
                        return filePathKiri.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrlKiri = task.getResult().toString();
                            Toast.makeText(activity_input_anak2.this, "Sukses Menerima Image Uri", Toast.LENGTH_SHORT).show();

                            SaveDataInfoToDatabase();
                        }
                    }
                });
            }
        });

        aplotTulisan.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String pesan = e.toString();
                Toast.makeText(activity_input_anak2.this, "Pesan Error : "+pesan, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(activity_input_anak2.this, "Upload Gambar Tamnpak Tulisan Berhasil", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = aplotTulisan.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        downloadImageUrlTulisan = filePathTulisan.getDownloadUrl().toString();
                        return filePathTulisan.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrlTulisan = task.getResult().toString();
                            Toast.makeText(activity_input_anak2.this, "Sukses Menerima Image Uri", Toast.LENGTH_SHORT).show();

                            SaveDataInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void SaveDataInfoToDatabase() {
        HashMap<String, Object> anakMap = new HashMap<>();
        anakMap.put("pid", dataRandomKey);
        anakMap.put("date", saveCurrentDate);
        anakMap.put("time", saveCurrentTime);
        anakMap.put("imageKiri", downloadImageUrlKiri);
        anakMap.put("imageKanan", downloadImageUrlKanan);
        anakMap.put("imageAtas", downloadImageUrlAtas);
        anakMap.put("imagePegang", downloadImageUrlPegang);
        anakMap.put("imageTulisan", downloadImageUrlTulisan);
        anakMap.put("aname", namaAnakUser);
        anakMap.put("umur", umurAnak);

        ref.child(dataRandomKey).updateChildren(anakMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            loadingBar.dismiss();
                            Toast.makeText(activity_input_anak2.this, "Data Anak Anda Berhasil Ditambahkan..", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity_input_anak2.this, activity_dashboard.class);
                            startActivity(intent);
                        }
                    }
                });

    }
    private void OpenGalleryTulisan() {
        Intent galleryUntent = new Intent();
        galleryUntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryUntent.setType("image/*");
        startActivityForResult(galleryUntent,GalleryPick);
    }

    private void OpenGalleryPegang() {
        Intent galleryUntent = new Intent();
        galleryUntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryUntent.setType("image/*");
        startActivityForResult(galleryUntent,GalleryPick);
    }

    private void OpenGalleryAtas() {
        Intent galleryUntent = new Intent();
        galleryUntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryUntent.setType("image/*");
        startActivityForResult(galleryUntent,GalleryPick);
    }

    private void OpenGalleryKanan() {
        Intent galleryUntent = new Intent();
        galleryUntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryUntent.setType("image/*");
        startActivityForResult(galleryUntent,GalleryPick);
    }

    private void OpenGalleryKiri() {
        Intent galleryUntent = new Intent();
        galleryUntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryUntent.setType("image/*");
        startActivityForResult(galleryUntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick && resultCode==RESULT_OK && data != null) {
            imageUriKiri = data.getData();
            imageUriKanan = data.getData();
            imageUriAtas = data.getData();
            imageUriPegang = data.getData();
            imageUriTulisan = data.getData();

            kiri.setImageURI(imageUriKiri);
            kanan.setImageURI(imageUriKanan);
            atas.setImageURI(imageUriAtas);
            pegang.setImageURI(imageUriPegang);
            tulisan.setImageURI(imageUriTulisan);
        }
    }
}
