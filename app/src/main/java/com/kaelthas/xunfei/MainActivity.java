package com.kaelthas.xunfei;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.kaelthas.xunfei.model.SpeakResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SpeechUtility.createUtility(MainActivity.this, SpeechConstant.APPID + "=57871f3e");

        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useDefaultUI2();
            }
        });

    }


    public void useDefaultUI2() {
        //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(MainActivity.this, null);
//2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
//3.开始听写
        mIat.startListening(mRecoListener);


    }

    //听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener() {
        //听写结果回调接口(返回Json格式结果，用户可参见附录12.1)；
        //一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
//关于解析Json的代码可参见MscDemo中JsonParser类；
//isLast等于true时会话结束。

        private StringBuilder result=new StringBuilder();
        public void onResult(RecognizerResult results, boolean isLast) {
//            Log.d("Result:", results.getResultString());
            if (!isLast){
//                JSONObject jsonObject;
//                try {
//                     jsonObject=new JSONObject(results.getResultString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
               SpeakResult speakResult= new Gson().fromJson(results.getResultString(), SpeakResult.class);


                result.append(speakResult.getResult());
            }



        }

        //会话发生错误回调接口
        public void onError(SpeechError error) {
            error.getPlainDescription(true); //获取错误码描述
        }

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        //开始录音
        public void onBeginOfSpeech() {
        }

        //音量值0~30
        //结束录音
        public void onEndOfSpeech() {
            Log.d("录音结束","————>"+result.toString());

            Toast.makeText(getApplicationContext(),result.toString(),Toast.LENGTH_LONG).show();
            result.delete(0,result.length());
        }

        //扩展用接口
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };


    private void useDefaultUI() {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(this, mInitListener); //2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin"); //若要将UI控件用于语义理解,必须添加以下参数设置,设置之后onResult回调返回将是语义理解 //结果
        mDialog.setParameter("asr_sch", "1");
        mDialog.setParameter("nlp_version", "2.0");
//3.设置回调接口
        mDialog.setListener(mRecognizerDialogListener);
// 4.显示dialog,接收语音输入
        mDialog.show();
    }

    RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            Log.e("mRecognizer-->onResult", "---" + recognizerResult.toString());
        }

        @Override
        public void onError(SpeechError speechError) {
            Log.e("mRecognizer-->onError", "---" + speechError.toString());
        }
    };

    InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int i) {

        }
    };
}
