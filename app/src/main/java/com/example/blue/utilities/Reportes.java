package com.example.blue.utilities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.blue.R;
import com.example.blue.models.animo;
import com.pdfview.PDFView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Reportes extends AppCompatActivity {

    PDFView pdfvista;

    ArrayList<animo> animoArrayList =new ArrayList<>();

    animo animos;

    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    String tituloText = "Reporte del estado de ánimo del paciente";

    String nombrepacientetxt ="Nombre del paciente:";

    String fechatext= "Generado en la fecha de: "+date;

    String descripciontxt= "El siguente documento muestra los estados de ánimo registrados por el paciente a lo\n" +
            "largo del mes hasta la fecha actual en la que se genera el reporte. En la parte inferior\n"+
            "se encuentran los días del mes.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        String idpaciente = getIntent().getStringExtra("ID_usuario");
        String nombre = getIntent().getStringExtra("Nombre");
        String apellidos = getIntent().getStringExtra("Apellidos");


        Button generar = findViewById(R.id.Generar);
        String URL = Constants.IP_ADDRESS+"ObtenerAnimo.php?id="+idpaciente;

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;


                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        String id= jsonObject.getString("ID_animo");
                        String animo = jsonObject.getString("Animo");
                        String fecha=jsonObject.getString("Fecha");
                        String id_paciente=jsonObject.getString("ID_paciente");

                        animos = new animo( id, animo, fecha, id_paciente);
                        animoArrayList.add(animos);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No hay ánimo registrado", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);

        pdfvista =findViewById(R.id.vistaPdf);

        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfDocument pdfDocument = new PdfDocument();
                Paint paint = new Paint();
                //Paint paintrec = new Paint();
                TextPaint titulo = new TextPaint();
                TextPaint fecha = new TextPaint();
                TextPaint paciente = new TextPaint();
                TextPaint descripcion = new TextPaint();
                TextPaint subtitulog = new TextPaint();
                TextPaint dias = new TextPaint();

                Bitmap bitmap, bitmapEscala, icono1, icono2, icono3, icono4, icono5;

                PdfDocument.PageInfo paginaInfo = new PdfDocument.PageInfo.Builder(792, 1120, 1).create();
                PdfDocument.Page pagina1 = pdfDocument.startPage(paginaInfo);

                Canvas canvas = pagina1.getCanvas();

                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logoblue2);
                bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 80, false);
                canvas.drawBitmap(bitmapEscala, 368, 20, paint);

                titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                titulo.setTextSize(35);
                canvas.drawText(tituloText, 70, 180, titulo);

                canvas.drawLine(80, 200, 720, 200, paint);

                fecha.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                fecha.setTextSize(20);
                canvas.drawText(fechatext, 15, 240, fecha);

                paciente.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                paciente.setTextSize(20);
                canvas.drawText(nombrepacientetxt+" "+nombre+" "+apellidos, 15, 265, paciente);

                descripcion.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                descripcion.setTextSize(20);
                String[] arrDescripcion = descripciontxt.split("\n");
                int y = 310;
                for(int i = 0 ; i < arrDescripcion.length ; i++) {
                    canvas.drawText(arrDescripcion[i], 15, y, descripcion);
                    y += 25;
                }

                //linea horizontal
                canvas.drawLine(80, 900, 720, 900, paint);
                //linea vertical
                canvas.drawLine(80, 400, 80, 900, paint);

                icono1 = BitmapFactory.decodeResource(getResources(), R.drawable.btnpesimo);
                bitmapEscala = Bitmap.createScaledBitmap(icono1, 60, 60, false);
                canvas.drawBitmap(bitmapEscala, 15, 810, paint);
                canvas.drawLine(70, 800, 90, 800, paint);

                icono2 = BitmapFactory.decodeResource(getResources(), R.drawable.btnmal);
                bitmapEscala = Bitmap.createScaledBitmap(icono2, 60, 60, false);
                canvas.drawBitmap(bitmapEscala, 15, 710, paint);
                canvas.drawLine(70, 700, 90, 700, paint);

                icono3 = BitmapFactory.decodeResource(getResources(), R.drawable.btnregular);
                bitmapEscala = Bitmap.createScaledBitmap(icono3, 60, 60, false);
                canvas.drawBitmap(bitmapEscala, 15, 610, paint);
                canvas.drawLine(70, 600, 90, 600, paint);

                icono4 = BitmapFactory.decodeResource(getResources(), R.drawable.btnbien);
                bitmapEscala = Bitmap.createScaledBitmap(icono4, 60, 60, false);
                canvas.drawBitmap(bitmapEscala, 15, 510, paint);
                canvas.drawLine(70, 500, 90, 500, paint);

                icono5 = BitmapFactory.decodeResource(getResources(), R.drawable.btnsuper);
                bitmapEscala = Bitmap.createScaledBitmap(icono5, 60, 60, false);
                canvas.drawBitmap(bitmapEscala, 15, 410, paint);
                canvas.drawLine(70, 400, 90, 400, paint);

                int left=95, right=110,x=95, i=1;

                dias.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                dias.setTextSize(10);

                for(int j=0; j<animoArrayList.size(); j++)
                {
                    if(animoArrayList.get(j).getAnimo().equals("1"))
                    {
                        paint.setColor(Color.rgb(142,198,206));
                        canvas.drawRect(left, 800,right,900,paint);
                    }
                    else if (animoArrayList.get(j).getAnimo().equals("2"))
                    {
                        paint.setColor(Color.rgb(195,138,208));
                        canvas.drawRect(left,700,right,900,paint);
                    }
                    else if(animoArrayList.get(j).getAnimo().equals("3"))
                    {
                        paint.setColor(Color.rgb(243,188,103));
                        canvas.drawRect(left,600,right,900,paint);
                    }
                    else if(animoArrayList.get(j).getAnimo().equals("4"))
                    {
                        paint.setColor(Color.rgb(235,216,0));
                        canvas.drawRect(left,500,right,900,paint);
                    }
                    else if (animoArrayList.get(j).getAnimo().equals("5"))
                    {
                        paint.setColor(Color.rgb(126,199,124));
                        canvas.drawRect(left,400,right,900,paint);
                    }

                    String[] aniodiames = animoArrayList.get(j).getFecha().split("-");

                    left+=20;
                    right+=20;
                    canvas.drawText(aniodiames[2],x,920, dias);
                    x+=20;
                    i++;
                }

                if(animoArrayList.size()==0)
                {
                    subtitulog.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    subtitulog.setTextSize(40);
                    canvas.drawText("Mes", 300, 1000, subtitulog);
                }
                else if(animoArrayList.size()==1)
                {
                    String[] aniodiames = animoArrayList.get(0).getFecha().split("-");
                    subtitulog.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    subtitulog.setTextSize(40);
                    if(aniodiames[1].equals("01"))
                    {
                        canvas.drawText("Enero",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("02"))
                    {
                        canvas.drawText("Febrero",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("03"))
                    {
                        canvas.drawText("Marzo",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("04"))
                    {
                        canvas.drawText("Abril",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("05"))
                    {
                        canvas.drawText("Mayo",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("06"))
                    {
                        canvas.drawText("Junio",20,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("07"))
                    {
                        canvas.drawText("Julio",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("08"))
                    {
                        canvas.drawText("Agosto",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("09"))
                    {
                        canvas.drawText("Septiembre",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("10"))
                    {
                        canvas.drawText("Octubre",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("11"))
                    {
                        canvas.drawText("Noviembre",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("12"))
                    {
                        canvas.drawText("Diciembre",270,1000,subtitulog);
                    }
                }
                else
                {
                    String[] aniodiames = animoArrayList.get(1).getFecha().split("-");
                    subtitulog.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    subtitulog.setTextSize(40);
                    if(aniodiames[1].equals("01"))
                    {
                        canvas.drawText("Enero",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("02"))
                    {
                        canvas.drawText("Febrero",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("03"))
                    {
                        canvas.drawText("Marzo",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("04"))
                    {
                        canvas.drawText("Abril",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("05"))
                    {
                        canvas.drawText("Mayo",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("06"))
                    {
                        canvas.drawText("Junio",20,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("07"))
                    {
                        canvas.drawText("Julio",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("08"))
                    {
                        canvas.drawText("Agosto",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("09"))
                    {
                        canvas.drawText("Septiembre",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("10"))
                    {
                        canvas.drawText("Octubre",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("11"))
                    {
                        canvas.drawText("Noviembre",270,1000,subtitulog);
                    }
                    else if(aniodiames[1].equals("12"))
                    {
                        canvas.drawText("Diciembre",270,1000,subtitulog);
                    }
                }

                paint.setColor(Color.BLUE);

                pdfDocument.finishPage(pagina1);

                File nuevaCarpeta = new File(Environment.getExternalStorageDirectory()+"/Documents", "Blue");
                if(!nuevaCarpeta.exists())
                {
                    nuevaCarpeta.mkdirs();
                }

                String documento= apellidos+" "+date+".pdf";

                File file = new File(nuevaCarpeta,documento);
                try {
                    pdfDocument.writeTo(new FileOutputStream(file));
                    Toast.makeText(getApplicationContext(), "El PDF se guardó en /Documents/Blue", Toast.LENGTH_LONG).show();
                    File filemostrar = new File(Environment.getExternalStorageDirectory()+"/Documents/Blue", documento);
                    pdfvista.fromFile(file);
                    pdfvista.isZoomEnabled();
                    pdfvista.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                pdfDocument.close();
            }
        });


    }
}