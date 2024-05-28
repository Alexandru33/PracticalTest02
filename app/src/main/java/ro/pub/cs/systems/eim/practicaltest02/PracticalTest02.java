package ro.pub.cs.systems.eim.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ro.pub.cs.systems.eim.practicaltest02.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02.network.ClientThread;
import ro.pub.cs.systems.eim.practicaltest02.network.ServerThread;

public class PracticalTest02 extends AppCompatActivity {

    private EditText editTextServerPort = null;
    private EditText editTextClientPort = null;
    private Button buttonServer = null;
    private Button buttonClient = null;

    private TextView textViewResponse = null;
    private EditText editTextWord = null;


    private ServerThread serverThread = null ;

    private class ClientButtonListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {

            String clientPort = editTextClientPort.getText().toString();
            String requestedWord  = editTextWord.getText().toString();


            if (clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (requestedWord.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Parameters from client (word) should be filled", Toast.LENGTH_SHORT).show();
                return;
            }
            if (requestedWord.contains(" ")) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Parameters from client (word) should not have spaces", Toast.LENGTH_SHORT).show();
                return;
            }

            textViewResponse.setText(Constants.EMPTY_STRING);

            ClientThread clientThread = new ClientThread(
                    Integer.parseInt(clientPort), requestedWord, textViewResponse
            );
            clientThread.start();

        }
    }
    private class ServerButtonListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {

            String serverPort = editTextServerPort.getText().toString();

            if (serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }

            serverThread = new ServerThread(Integer.parseInt(serverPort));

            if (serverThread.getServerSocket() == null) {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
                return;
            }
            serverThread.start();

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);


        editTextServerPort = (EditText) findViewById(R.id.editTextPortServer);
        editTextClientPort = (EditText) findViewById(R.id.editTextPortClient);
        editTextWord = (EditText) findViewById(R.id.editTextWord);


        buttonClient = (Button) findViewById(R.id.buttonClient);
        buttonServer = (Button) findViewById(R.id.buttonServer);

        textViewResponse = (TextView) findViewById(R.id.textViewResponse);




        buttonClient.setOnClickListener( new ClientButtonListener());
        buttonServer.setOnClickListener(new ServerButtonListener());





    }

}