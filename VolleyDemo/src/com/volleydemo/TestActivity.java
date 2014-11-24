package com.volleydemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by fangzhu.du on 2014/9/19.
 */
public class TestActivity extends Activity {
    ImageView imageView = null;
    Button button = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test);

        addDesktop("oncreate");

        imageView = (ImageView) findViewById(R.id.img);
        button = (Button) findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bitmap bitmap = getAppIcon(2);
//                imageView.setImageBitmap(bitmap);
//                addShortcutToDesktop(bitmap);

                addDesktop("click");
            }
        });
    }


    private void addDesktop(String name) {

        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");   //快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        shortcut.putExtra("duplicate", false); //不允许重复创建
        //指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer
        //注意: ComponentName的第二个参数必须加上点号(.)，否则快捷方式无法启动相应程序         //
        int iconjp = R.drawable.icon_qq;
        String packagepach = "";

        if (this.getLocalClassName().startsWith("com.volleydemo.")) {
            packagepach = this.getLocalClassName();
        } else {
            packagepach = "com.volleydemo." + this.getLocalClassName();
        }
        ComponentName comp = new ComponentName(this.getPackageName(), packagepach);
        //

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(comp);
        intent.putExtra("gohome", "true");
        intent.putExtra("isShortcut", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        //快捷方式的图标
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, iconjp);

        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

        sendBroadcast(shortcut);

        int sysVersion = Integer.parseInt(Build.VERSION.SDK);
        if (sysVersion == 8) {
            Toast.makeText(this, "往桌面添加快捷方式", Toast.LENGTH_SHORT).show();
        }
    }


    private void deleteShortcut() {

        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");


        //快捷方式的名称

        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));

        String appClass = this.getPackageName() + "." + this.getLocalClassName();

        ComponentName comp = new ComponentName(this.getPackageName(), appClass);

        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));


        sendBroadcast(shortcut);


    }

    /*
       * 删除程序的快捷方式
     */
    private void delShortcut() {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");

        //快捷方式的名称       www.2cto.com
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));

        //指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer
        //注意: ComponentName的第二个参数必须是完整的类名（包名+类名），否则无法删除快捷方式
        String appClass = this.getPackageName() + "." + this.getLocalClassName();
        ComponentName comp = new ComponentName(this.getPackageName(), appClass);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));
        //发送卸载快捷方式的图标
        sendBroadcast(shortcut);


    }


    void addShortcutToDesktop(Bitmap icon) {

//        Intent shortcut = new Intent(ACTION_INSTALL);
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");   //快捷方式的名称
        BitmapDrawable iconBitmapDrawabel = null;

        // 获取应用基本信息
        String label = this.getPackageName();
        PackageManager packageManager = getPackageManager();
        try {
            iconBitmapDrawabel = (BitmapDrawable) packageManager.getApplicationIcon(label);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // 设置属性
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, label);
//        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, iconBitmapDrawabel.getBitmap());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);//这里可以动态更新图标的

        // 是否允许重复创建 -- fase-->否
        shortcut.putExtra("duplicate", false);

        // 设置启动程序
        ComponentName comp = new ComponentName(label, "." + this.getLocalClassName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));

        sendBroadcast(shortcut);
    }


    private Bitmap getAppIcon(int number) {

        //初始化画布
        int iconSize = (int) getResources().getDimension(android.R.dimen.app_icon_size);
        Bitmap contactIcon = Bitmap.createBitmap(iconSize, iconSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(contactIcon);

        //拷贝图片
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon_qq);
        Paint paint = new Paint();
        paint.setDither(true);//防抖动
        paint.setFilterBitmap(true);//用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯齿的效果
        Rect src = new Rect(0, 0, icon.getWidth(), icon.getHeight());
        Rect dst = new Rect(0, 0, iconSize + 15, iconSize + 15);
        canvas.drawBitmap(icon, src, dst, paint);


        //drawCircle
        int radius = 30;
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(iconSize - 18, 25, radius, paint);


        int contacyCount = 11;
        Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
        countPaint.setColor(Color.RED);
        countPaint.setTextSize(20f);
        countPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(String.valueOf(contacyCount), iconSize - 18, 25, countPaint);
        return contactIcon;

    }


    public static void main(String[] args) {

//        String s = "123456789098765f";怎么转换成char[] c={0x21,0x43,0x65.....,0xf5};

        String s = "123456789098765f";
//        char[]c = s.toCharArray();
//        for (int i = 0; i < c.length; i++) {
//            System.out.println(c[i]);
//        }
//        System.out.println(s.length());
//        System.out.println(Integer.parseInt("12", 16));
//        System.out.println(0x43);

        char c = 0xf5;
//        System.out.println();
//        getCharFromString(s);
    }


    //string转化char[]
    public static char[] getCharFromString(String s) {
        s = s + "0";
        char[] mChar = s.toCharArray();
        System.out.println("mChar.length:" + mChar.length);
        int[] toInt = new int[mChar.length];
        int[] resultInt = new int[8];
            /*for(int i=0;i<mChar.length;i++){
                 toInt[i] = Integer.parseInt(String.valueOf(mChar[i]));
            }
            for(int i=0;i<mChar.length;i=i+2){
               resultInt[i/2]=toInt[i]+toInt[i+1]*16;
            }*/
        String[] myStr = new String[8];
        for (int i = 0; i < mChar.length; i = i + 2) {
            char[] tempChar = new char[2];
            tempChar[0] = mChar[i + 1];
            tempChar[1] = mChar[i];
            myStr[i] = new String(tempChar);
        }
        char[] result = new char[8];
        for (int i = 0; i < 8; i++) {
            System.out.println("0919" + "myStr[" + i + "]:" + myStr[i]);
            int a = Integer.parseInt(myStr[i], 16);
            result[i] = (char) a;
            System.out.println("0919" + "result[" + i + "]:" + result[i]);
        }

        return result;
    }

}
