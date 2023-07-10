package com.example.blue.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.blue.R;
import com.example.blue.database.NotesDatabase;
import com.example.blue.entities.Note;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText inputNoteTitle, inputNoteSubtitle, inputNoteText;
    private TextView textDateTime;
    private View viewSubtitleIndicator;
    private ImageView imageNote;

    private ImageView audioNote;
    private ImageView audioNotePause;

    private String selectedNoteColor;
    private String selectedImagePath;

    private String selectedAudioPath;

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;

    private static final int REQUEST_CODE_SELECT_AUDIO = 3;

    private AlertDialog dialogDeleteNote;

    private Note alreadyAvailableNote;

    private TextView titleTextView;
    private ImageView iconAudioView;
    private LinearLayout layoutAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        // BOTÓN ATRÁS
        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        inputNoteTitle = findViewById(R.id.inputNoteTitle);
        inputNoteSubtitle = findViewById(R.id.inputNoteSubtitle);
        inputNoteText = findViewById(R.id.inputNote);
        textDateTime = findViewById(R.id.textDateTime);
        viewSubtitleIndicator = findViewById(R.id.viewSubtitleIndicator);
        imageNote = findViewById(R.id.imageNote);
        audioNote = findViewById(R.id.audioNote);
        audioNotePause = findViewById(R.id.audioNotePause);

        // PARA AGREGAR LA FECHA EN LA NOTA
        textDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())
        );

        // PARA GUARDAR LA NOTA
        ImageView imagesave = findViewById(R.id.imageSave);
        imagesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        selectedNoteColor = "#69D2E7";
        selectedImagePath = "";

        selectedAudioPath = "";

        if(getIntent().getBooleanExtra("isViewOrUpdate", false)){
            alreadyAvailableNote = (Note) getIntent().getSerializableExtra("note");
            setViewOrUpdate();
        }

        // Para eliminar la imagen
        findViewById(R.id.imageRemoveImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageNote.setImageBitmap(null);
                imageNote.setVisibility(View.GONE);
                findViewById(R.id.imageRemoveImage).setVisibility(View.GONE);
                selectedImagePath = "";
            }
        });

        // Para eliminar el audio
        findViewById(R.id.imageRemoveAudio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioNote.setImageBitmap(null);
                audioNote.setVisibility(View.GONE);
                findViewById(R.id.imageRemoveAudio).setVisibility(View.GONE);
                findViewById(R.id.audioNotePause).setVisibility(View.GONE);
                selectedAudioPath = "";

            }
        });

        if(getIntent().getBooleanExtra("isFromQuickActions", false)){
            String type = getIntent().getStringExtra("quickActionType");
            if(type != null){
                if(type.equals("image")){
                    selectedImagePath = getIntent().getStringExtra("imagePath");
                    imageNote.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
                    imageNote.setVisibility(View.VISIBLE);
                    findViewById(R.id.imageRemoveImage).setVisibility(View.VISIBLE);
                }
            }
        }

        initMiscellaneous();
        setSubtitleIndicatorColor();
    }

    // PARA VER O ACTUALIZAR LA NOTA
    private void setViewOrUpdate(){
        inputNoteTitle.setText(alreadyAvailableNote.getTitle());
        inputNoteSubtitle.setText(alreadyAvailableNote.getSubtitle());
        inputNoteText.setText(alreadyAvailableNote.getNoteText());
        textDateTime.setText(alreadyAvailableNote.getDateTime());

        if(alreadyAvailableNote.getImagePath() != null && !alreadyAvailableNote.getImagePath().trim().isEmpty()){
            imageNote.setImageBitmap(BitmapFactory.decodeFile(alreadyAvailableNote.getImagePath()));
            imageNote.setVisibility(View.VISIBLE);
            findViewById(R.id.imageRemoveImage).setVisibility(View.VISIBLE);
            selectedImagePath = alreadyAvailableNote.getImagePath();
        }

        if(alreadyAvailableNote.getAudioPath() != null && !alreadyAvailableNote.getAudioPath().trim().isEmpty()){
            audioNote.setImageBitmap(BitmapFactory.decodeFile(alreadyAvailableNote.getAudioPath()));
            audioNote.setVisibility(View.VISIBLE);
            findViewById(R.id.imageRemoveAudio).setVisibility(View.VISIBLE);
            //findViewById(R.id.audioNotePause).setVisibility(View.VISIBLE);
            selectedAudioPath = alreadyAvailableNote.getAudioPath();

            audioNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(alreadyAvailableNote.getAudioPath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                    findViewById(R.id.audioNote).setVisibility(View.INVISIBLE);
                    findViewById(R.id.audioNotePause).setVisibility(View.VISIBLE);
                    final LinearLayout layoutMiscellaneous = findViewById(R.id.layoutMiscellaneous);
                    layoutMiscellaneous.findViewById(R.id.layoutAddAudio).setVisibility(View.GONE);


                    audioNotePause.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mediaPlayer.pause();
                            findViewById(R.id.audioNote).setVisibility(View.VISIBLE);
                            findViewById(R.id.audioNotePause).setVisibility(View.INVISIBLE);
                            layoutMiscellaneous.findViewById(R.id.layoutAddAudio).setVisibility(View.VISIBLE);


                            audioNote.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mediaPlayer.start();
                                    findViewById(R.id.audioNote).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.audioNotePause).setVisibility(View.VISIBLE);
                                    layoutMiscellaneous.findViewById(R.id.layoutAddAudio).setVisibility(View.GONE);
                                }
                            });
                        }
                    });
                    findViewById(R.id.imageRemoveAudio).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            audioNote.setImageBitmap(null);
                            audioNote.setVisibility(View.GONE);
                            findViewById(R.id.imageRemoveAudio).setVisibility(View.GONE);
                            findViewById(R.id.audioNotePause).setVisibility(View.GONE);
                            selectedAudioPath = "";
                            mediaPlayer.release();

                        }
                    });
                    findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                            mediaPlayer.stop();
                        }
                    });

                    findViewById(R.id.imageSave).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveNote();
                            mediaPlayer.stop();
                        }
                    });

                    final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(layoutMiscellaneous);
                    layoutMiscellaneous.findViewById(R.id.layoutDeleteNote).setVisibility(View.VISIBLE);
                    layoutMiscellaneous.findViewById(R.id.layoutDeleteNote).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            showDeleteNoteDialog();
                            mediaPlayer.stop();
                        }
                    });

                }
            });

        }

    }


    // PARA GUARDAR LA NOTA
    private void saveNote(){
        if(inputNoteTitle.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "El título no puede estar vacío", Toast.LENGTH_SHORT).show();
            return;
        }else if(inputNoteText.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "¡No olvides escribir texto!", Toast.LENGTH_SHORT).show();
            return;
        }

        final Note note = new Note();
        note.setTitle(inputNoteTitle.getText().toString());
        note.setSubtitle(inputNoteSubtitle.getText().toString());
        note.setNoteText(inputNoteText.getText().toString());
        note.setDateTime(textDateTime.getText().toString());
        note.setColor(selectedNoteColor);
        note.setImagePath(selectedImagePath);

        note.setAudioPath(selectedAudioPath);


        // se asigna el id de una nueva nota de una nota ya disponible. Como se tiene set onConflictStrategy en "REPLACE" en NOTEDAO.
        // esto significa que si el id de una nueva nota ya está en la base de datos será reemplazado con una nueva nota o la nota
        // será actualizada
        if(alreadyAvailableNote != null){
            note.setId(alreadyAvailableNote.getId());
        }

        @SuppressLint("StaticFieldLeak")
        class SaveNoteTask extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids){
                NotesDatabase.getDatabase(getApplicationContext()).noteDao().insertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }

        new SaveNoteTask().execute();
    }

    // INICIAR LA PESTAÑA DE OPCIONES (MISCELLANEOUS)
    private void initMiscellaneous(){
        final LinearLayout layoutMiscellaneous = findViewById(R.id.layoutMiscellaneous);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(layoutMiscellaneous);
        layoutMiscellaneous.findViewById(R.id.textMiscellaneous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        final ImageView imageColor1 = layoutMiscellaneous.findViewById(R.id.imageColor1);
        final ImageView imageColor2 = layoutMiscellaneous.findViewById(R.id.imageColor2);
        final ImageView imageColor3 = layoutMiscellaneous.findViewById(R.id.imageColor3);
        final ImageView imageColor4 = layoutMiscellaneous.findViewById(R.id.imageColor4);
        final ImageView imageColor5 = layoutMiscellaneous.findViewById(R.id.imageColor5);

        // PARA CAMBIAR EL COLOR A LA NOTA :D
        layoutMiscellaneous.findViewById(R.id.viewColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNoteColor = "#69D2E7";
                imageColor1.setImageResource(R.drawable.ic_done);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setSubtitleIndicatorColor();
            }
        });

        layoutMiscellaneous.findViewById(R.id.viewColor2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNoteColor = "#E8AA43";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(R.drawable.ic_done);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setSubtitleIndicatorColor();
            }
        });

        layoutMiscellaneous.findViewById(R.id.viewColor3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNoteColor = "#F58179";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(R.drawable.ic_done);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setSubtitleIndicatorColor();
            }
        });

        layoutMiscellaneous.findViewById(R.id.viewColor4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNoteColor = "#A7DBD8";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(R.drawable.ic_done);
                imageColor5.setImageResource(0);
                setSubtitleIndicatorColor();
            }
        });

        layoutMiscellaneous.findViewById(R.id.viewColor5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNoteColor = "#B194BB";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(R.drawable.ic_done);
                setSubtitleIndicatorColor();
            }
        });

        if(alreadyAvailableNote != null && alreadyAvailableNote.getColor() != null && !alreadyAvailableNote.getColor().trim().isEmpty()){
            switch (alreadyAvailableNote.getColor()){
                case "#E8AA43":
                    layoutMiscellaneous.findViewById(R.id.viewColor2).performClick();
                    break;
                case "#F58179":
                    layoutMiscellaneous.findViewById(R.id.viewColor3).performClick();
                    break;
                case "#A7DBD8":
                    layoutMiscellaneous.findViewById(R.id.viewColor4).performClick();
                    break;
                case "#B194BB":
                    layoutMiscellaneous.findViewById(R.id.viewColor5).performClick();
                    break;
            }
        }

        // SELECCIONAR IMAGEN O AUDIO DE LA PESTAÑA OPCIONES
        layoutMiscellaneous.findViewById(R.id.layoutAddImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(CreateNoteActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION);
                }else{
                    selectImage();
                }
            }
        });

        layoutMiscellaneous.findViewById(R.id.layoutAddAudio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(CreateNoteActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION);
                }else{
                    selectAudio();
                }
            }
        });

        // PARA ELIMINAR LA NOTA
        if(alreadyAvailableNote != null){
            layoutMiscellaneous.findViewById(R.id.layoutDeleteNote).setVisibility(View.VISIBLE);
            layoutMiscellaneous.findViewById(R.id.layoutDeleteNote).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    showDeleteNoteDialog();
                }
            });
        }
    }

    // DIÁLOGO PARA ELIMINAR LA NOTA
    private void showDeleteNoteDialog() {

        if (dialogDeleteNote == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateNoteActivity.this);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_delete_note, (ViewGroup) findViewById(R.id.layoutDeleteNoteContainer));
            builder.setView(view);
            dialogDeleteNote = builder.create();
            if (dialogDeleteNote.getWindow() != null) {
                dialogDeleteNote.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            view.findViewById(R.id.textDeleteNote).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    @SuppressLint("StaticFieldLeak")
                    class DeleteNoteTask extends AsyncTask<Void, Void, Void> {

                        @Override
                        protected Void doInBackground(Void... voids) {
                            NotesDatabase.getDatabase(getApplicationContext()).noteDao().deleteNote(alreadyAvailableNote);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Intent intent = new Intent();
                            intent.putExtra("isNoteDeleted", true);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }

                    new DeleteNoteTask().execute();
                }
            });

            view.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogDeleteNote.dismiss();
                }
            });
        }
        dialogDeleteNote.show();
    }

    // SELECCIONAR EL COLOR DE LA NOTA
    private void setSubtitleIndicatorColor(){
        GradientDrawable gradientDrawable = (GradientDrawable) viewSubtitleIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedNoteColor));
    }

    // SELECCIONAR LA IMAGEN
    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }

    // SELECCIONAR EL AUDIO
    private void selectAudio(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, REQUEST_CODE_SELECT_AUDIO);
        }
    }

    // REQUERIMIENTO DE PERMISOS
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage();
            }else{
                Toast.makeText(this, "¡Permiso denegado!", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectAudio();
            }else{
                Toast.makeText(this, "¡Permiso denegado!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    // resultado de manejo para la imagen y el audio seleccionados
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK){
            if(data != null){
                Uri selectedImageUri = data.getData();
                if(selectedImageUri != null){
                    try {

                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageNote.setImageBitmap(bitmap);
                        imageNote.setVisibility(View.VISIBLE);
                        findViewById(R.id.imageRemoveImage).setVisibility(View.VISIBLE);

                        selectedImagePath = getPathFromUri(selectedImageUri);

                    }catch (Exception exception){
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            // REPRODUCIR AUDIO
        }else if(requestCode == REQUEST_CODE_SELECT_AUDIO && resultCode == RESULT_OK){
            if(data != null){
                Uri selectedAudioUri = data.getData();
                if(selectedAudioUri != null){
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedAudioUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        audioNote.setImageBitmap(bitmap);
                        audioNote.setVisibility(View.VISIBLE);
                        findViewById(R.id.imageRemoveAudio).setVisibility(View.VISIBLE);
                        selectedAudioPath = getPathFromUri(selectedAudioUri);
                        MediaPlayer mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        audioNote.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    mediaPlayer.setDataSource(getApplicationContext(), selectedAudioUri);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    mediaPlayer.prepare();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                mediaPlayer.start();
                                findViewById(R.id.audioNote).setVisibility(View.INVISIBLE);
                                findViewById(R.id.audioNotePause).setVisibility(View.VISIBLE);
                                final LinearLayout layoutMiscellaneous = findViewById(R.id.layoutMiscellaneous);
                                layoutMiscellaneous.findViewById(R.id.layoutAddAudio).setVisibility(View.GONE);

                                audioNotePause.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mediaPlayer.pause();
                                        findViewById(R.id.audioNote).setVisibility(View.VISIBLE);
                                        findViewById(R.id.audioNotePause).setVisibility(View.INVISIBLE);
                                        layoutMiscellaneous.findViewById(R.id.layoutAddAudio).setVisibility(View.VISIBLE);

                                        audioNote.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mediaPlayer.start();
                                                findViewById(R.id.audioNote).setVisibility(View.INVISIBLE);
                                                findViewById(R.id.audioNotePause).setVisibility(View.VISIBLE);
                                                layoutMiscellaneous.findViewById(R.id.layoutAddAudio).setVisibility(View.GONE);
                                            }
                                        });
                                    }
                                });
                                findViewById(R.id.imageRemoveAudio).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        audioNote.setImageBitmap(null);
                                        audioNote.setVisibility(View.GONE);
                                        findViewById(R.id.imageRemoveAudio).setVisibility(View.GONE);
                                        findViewById(R.id.audioNotePause).setVisibility(View.GONE);
                                        selectedAudioPath = "";
                                        mediaPlayer.release();
                                    }
                                });
                                findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        onBackPressed();
                                        mediaPlayer.stop();
                                    }
                                });
                                findViewById(R.id.imageSave).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        saveNote();
                                        mediaPlayer.stop();
                                    }
                                });

                            }
                        });
                    }catch (Exception exception){
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private String getPathFromUri(Uri contentUri){
        String filePath;
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if(cursor == null){
            filePath = contentUri.getPath();
        }else{
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }
}