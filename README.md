# PasswordBox
a password widget suports round corners and chosen color.

![](http://upload-images.jianshu.io/upload_images/4824158-e026efbddb861bd3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### Gradle

```
dependencies {
   compile 'com.iceuncle:passwordbox:1.1.2'
}
```

### Usage

```
    <com.iceuncle.passwordbox.PasswordBox
        android:id="@+id/password_view"
        android:layout_width="240dp"
        android:layout_height="40dp"
        android:layout_centerInParent="true"
        app:pwdBackgroundColor="#FFFFFF"
        app:pwdChosenColor="#ff9c09"
        app:pwdNormalBorderColor="#c3c3c3"
        app:pwdTextSize="25sp"
        app:pwdViewCorners="6dp"
        app:pwdViewLineWidth="1dp" />
 ```
 
 Get password
 ```
 final PasswordBox passwordBox = (PasswordBox) findViewById(R.id.password_view);
        passwordBox.setInputCompleteListener(new PasswordBox.InputCompleteListener() {
            @Override
            public void inputComplete() {
                Log.d("pwd", passwordBox.getStrPassword());
            }
        });
  ```
  
### introduction
* pwdBackgroundColor 背景色  background
* pwdChosenColor  选中色     chosen color
* pwdNormalBorderColor 默认边框色   default border color
* pwdTextSize  圆点字体大小   dot font size
* pwdViewCorners  圆角大小   round coner size 
* pwdViewLineWidth  边框线宽  border line width
