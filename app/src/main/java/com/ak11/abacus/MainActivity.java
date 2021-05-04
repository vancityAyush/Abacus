package com.ak11.abacus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @BindView(R.id.imageView1)
    ImageView img1;
    @BindView(R.id.imageView2)
    ImageView img2;
    @BindView(R.id.imageView3)
    ImageView img3;
    @BindView(R.id.imageView4)
    ImageView img4;
    @BindView(R.id.imageView5)
    ImageView img5;
    @BindView(R.id.imageView6)
    ImageView img6;
    @BindView(R.id.imageView7)
    ImageView img7;
    @BindView(R.id.imageView8)
    ImageView img8;
    @BindView(R.id.imageView9)
    ImageView img9;
    @BindView(R.id.imageView0)
    ImageView img0;

    @BindView(R.id.imageViewPlus)
    ImageView imgPlus;
    @BindView(R.id.imageViewMinus)
    ImageView imgMinus;
    @BindView(R.id.imageViewMultiply)
    ImageView imgMultiply;
    @BindView(R.id.imageViewDivide)
    ImageView imgDivide;
    @BindView(R.id.imageViewDot)
    ImageView imgDot;
    @BindView(R.id.imageViewBack)
    ImageView imgBack;


    @BindView(R.id.imageButtonEqual)
    ImageButton imgEqual;

    @BindView(R.id.textViewDisplay)
    TextView txtDisplay;
    int prev=0;
    String str="";
    ArrayList entries=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

            img1.setOnClickListener(MainActivity.this);
            img2.setOnClickListener(MainActivity.this);
            img3.setOnClickListener(MainActivity.this);
            img4.setOnClickListener(MainActivity.this);
            img5.setOnClickListener(MainActivity.this);
            img6.setOnClickListener(MainActivity.this);
            img7.setOnClickListener(MainActivity.this);
            img8.setOnClickListener(MainActivity.this);
            img9.setOnClickListener(MainActivity.this);
            img0.setOnClickListener(MainActivity.this);
            imgDot.setOnClickListener(MainActivity.this);
            imgPlus.setOnClickListener(MainActivity.this);
            imgMinus.setOnClickListener(MainActivity.this);
            imgMultiply.setOnClickListener(MainActivity.this);
            imgDivide.setOnClickListener(MainActivity.this);
            imgBack.setOnClickListener(MainActivity.this);
            imgEqual.setOnClickListener(MainActivity.this);

            imgBack.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    v.animate().rotationBy(360*10).setDuration(1000).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            imgBack.animate().rotation(0).translationX(0).translationY(0).start();
                        }
                    }).start();
                    reset();
                    return false;
                }
            });


    }

    @Override
    public void onClick(View v) {
        v.animate().rotationBy(360).setDuration(100).withEndAction(new Runnable() {
            @Override
            public void run() {
                imgBack.animate().rotation(0).translationX(0).translationY(0).start();
            }
        }).start();
        str = txtDisplay.getText().toString();
        switch (v.getId())
        {
            case R.id.imageView0:
                str+="0";
                break;
            case  R.id.imageView1:
                str+="1";
                break;
            case R.id.imageView2:
                str+="2";
                break;
            case R.id.imageView3:
                str+="3";
                break;
            case R.id.imageView4:
                str+="4";
                break;
            case R.id.imageView5:
                str+="5";
                break;
            case R.id.imageView6:
                str+="6";
                break;
            case R.id.imageView7:
                str+="7";
                break;
            case R.id.imageView8:
                str+="8";
                break;
            case R.id.imageView9:
                str+="9";
                break;
            case R.id.imageViewDivide:
                if(prev==str.length())
                    break;
                str+="÷";
                prev=str.length();
                break;
            case R.id.imageViewMultiply:
                if(prev==str.length())
                    break;
                str+="x";
                prev=str.length();
                break;
            case R.id.imageViewMinus:
                if(prev==str.length())
                    break;
                str+="-";
                prev=str.length();
                break;
            case R.id.imageViewPlus:
                if(prev==str.length())
                    break;
                str+="+";
                prev=str.length();
                break;
            case R.id.imageViewDot:
                //TODO check for double dot in a number
                if(prev==str.length())
                    break;
                str+=".";
                prev=str.length();
                break;
            case R.id.imageViewBack:
                try {
                    str=str.substring(0,str.length()-1);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.imageButtonEqual:
                tokenize();
                double result = calculate();
                str=result+"";
                Log.i("TAG",entries.toString());
//                Log.i("TAG",str.substring(prev));
//                int num = Integer.parseInt(str.substring(prev));

                break;

            }
            txtDisplay.setText(str);
        }
        private void tokenize() {
        entries=new ArrayList();
            double n = 0;
            for (char ch : str.toCharArray()) {
                if (Character.isDigit(ch)) {
                    n = n * 10 + Character.getNumericValue(ch);
                } else {
                    switch (ch) {
                        case '+':
                            entries.add(n);
                            entries.add('+');
                            n = 0;
                            break;
                        case '-':
                            entries.add(n);
                            entries.add('-');
                            n = 0;
                            break;
                        case 'x':
                            entries.add(n);
                            entries.add('x');
                            n = 0;
                            break;
                        case '÷':
                            entries.add(n);
                            entries.add('÷');
                            n = 0;
                            break;
                        case '.':
                            entries.add(n);
                            entries.add('.');
                            n = 0;
                            break;
                    }
                }
            }
            entries.add(n);
        }
        private void reset(){
            prev=0;
            txtDisplay.setText("");
            entries=new ArrayList();

        }

        double calculate(){
            final String BODMAS = ".÷x+-";
            double result=0d;
            int index=0;
                for (char sign : BODMAS.toCharArray()) {
                    int size=entries.size();
                    for (int i=0;i<size;i++) {
                        Object obj = entries.get(i);
                        if (obj instanceof Character) {
                            if ((Character) obj == sign) {
                                double n1 = (double) entries.get(index - 1);
                                double n2 = (double) entries.get(index + 1);
                                double tempResult = 0d;
                                switch (sign) {
                                    case '÷':
                                        tempResult = n1 / n2;
                                        break;
                                    case 'x':
                                        tempResult = n1 * n2;
                                        break;
                                    case '+':
                                        tempResult = n1 + n2;
                                        break;
                                    case '-':
                                        tempResult = n1 - n2;
                                        break;
                                    case '.':
                                        while((int)n2!=0){
                                            tempResult=(tempResult/10)+(n2%10);
                                            n2=(int)n2/10;
                                        }
                                        tempResult=n1+tempResult/10;
                                }
                                entries.remove(index - 1);
                                entries.remove(index);
                                entries.set(index - 1, tempResult);
                                size = entries.size();
                            }
                        }
                        index++;
                    }
                    index=0;
                }

            DecimalFormat df = new DecimalFormat("#.###");
            df.setRoundingMode(RoundingMode.CEILING);
            result = Double.parseDouble(df.format(entries.get(0)));
            //result= Double.parseDouble(df.format(result));
            return result;

        }

}