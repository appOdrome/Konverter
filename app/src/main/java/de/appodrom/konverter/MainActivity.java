package de.appodrom.konverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    // Variablen für die Buttons anlegen
    Button usd2euroButton;  // Buttonname: usd2euro
    Button euro2usdButton;  // Buttonname: euro2usd
    Button inch2cmButton;   // Buttonname: inch2cm
    Button cm2inchButton;   // Buttonname: cm2inch
    // Variablen für die Textfelder anlegen
    EditText eingabe;   // Textfeld heisst: input
    TextView ausgabe;   // Textfeld heisst: statusText


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Die Initialisierung der Buttons erfolgt hier im onCreeate, damit diese
        // Anweisungen nicht zusätzlich in jedem Button eingefügt werden müssen
        // Damit habe ich Zugriff auf die Buttons
        usd2euroButton = (Button)findViewById(R.id.usd2euro);
        euro2usdButton = (Button)findViewById(R.id.euro2usd);
        inch2cmButton = (Button)findViewById(R.id.inch2cm);
        cm2inchButton = (Button)findViewById(R.id.cm2inch);

        eingabe = (EditText) findViewById(R.id.input);
        ausgabe = (TextView) findViewById(R.id.statusText);

    }

    // Die Methode convert funktioniert hier als wiederverwendbarer onClick-Listener
    // mit dem alle Buttonfunktionen übersichtlich zusammengefasst werden können
    public void convert (View view)
    {
        // Eingabe des Anwenders übernehmen und überprüfen ob diese gültig ist
        // Das Eingabefeld kann über den Inputtype nur noch number, number decimal, number signed
        // entgegen nehmen, das modifiziert auch die verwendete onScreenTastatur gleich mit!
        String inputString = eingabe.getText().toString();

        // --------------- Eingabeprüfung ------------------------------------------------
        Double inputValue; // Wert muss hier wegen der Gültigkeit im try/catch vorher deklariert werden
        try {
            // Jetzt noch den String in einen Double umwandeln damit man damit rechnen kann!
            inputValue = Double.parseDouble(inputString);

        } catch (NumberFormatException e)
        {
            // Warnung erzeugen                  text aus strings nutzen (Übersetzung)
            Toast warnung = Toast.makeText(this, R.string.invalid_input, Toast.LENGTH_LONG);
            warnung.show();
            return;
        }


        // Button-ID abrufen und die gewünschte Methode zur Berechnung aufrufen
        // Diese Funktion ruft die Button-ID ab und mit der Fallunterscheidung (Case) kann dann die
        // entsprechende Berechnung vorgenommen werden
        switch (view.getId()){
            case R.id.euro2usd:
                euro2usd(inputValue); // Aufruf der entsprechenden Methode für die Berechnung
                break; // Beendet den switch-case wenn dieser Button geklickt wurde

            case R.id.usd2euro:
                usd2euro(inputValue);
                break;

            case R.id.inch2cm:
                inch2cm(inputValue);
                break;

            case R.id.cm2inch:
                cm2inch(inputValue);
                break;
        }
    }

    // ---------------  Methoden in der App --------------------------------------------

    // Resetfunktion zum Löschen einer vorhandenen Berechnungen
    public void reset(View view)
    {
        eingabe.setText("");  // Löscht vorhandenen Text im Eingabefeld
        ausgabe.setText("");  // Löscht vorhandenen Text im Ausgabefeld
    }

    // Hier folgen die Methoden, die die eigentlichen Berechnungen vornehmen
    // Euro in Dollar umrechnen
    protected void euro2usd(Double input)
    {
        showResult(input * 1.13, " $"); // Umrechnung Euro in Dollar
    }

    // Dollar in Euro umrechnen
    protected void usd2euro(Double input)
    {
        showResult(input / 1.13, " €"); // Umrechnung Dollar in Euro
    }

    // Inch in CM umrechnen
    protected void inch2cm(Double input)
    {
        showResult(input * 2.54, " cm"); // Umrechnung Ich in CM
    }

    // CM in Inch
    protected void cm2inch(Double input)
    {
        showResult(input / 2.54, " \""); // Umrechnung CM in Inch
    }

    // ------------------------------ Anzeige des Ergebnis ------------------------------
    // Methode zum Anzeigen des Brechnungsergebnis
    // targetUnit ist die "Einheit" die angezeigt werden soll (Dollar/Euro/Inch/cm)
    protected void showResult(Double result, String targetUnit)
    {
        // Rundet den Berechneten Wert und verkürzt das Ergebnis auf 2 Nachkommastellen
        java.math.BigDecimal gerundet = new BigDecimal(result);
        gerundet = gerundet.setScale(2, RoundingMode.HALF_UP);

        // Ausgabe des berechneten Ergebnisses
        String resultStr = getString(R.string.calculation_output) + gerundet + targetUnit;
        ausgabe.setVisibility(View.VISIBLE); // Ergebnisfeld sichtbar machen
        ausgabe.setText(resultStr);
    }
}
