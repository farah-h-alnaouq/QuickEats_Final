package com.example.quickeats;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FoodDetailsActivity extends AppCompatActivity {

    //  (بيبدأ من 1)
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        //  إعداد التولبار وسهم الرجوع
        Toolbar toolbar = findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // برمجة ضغطة السهم ليرجعنا home
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //  ربط العناصر البرمجية بالـ XML
        ImageView foodImage = findViewById(R.id.detail_image);
        TextView foodName = findViewById(R.id.detail_name);
        TextView foodPrice = findViewById(R.id.detail_price);
        TextView quantityText = findViewById(R.id.tv_quantity);

        ImageView btnMinus = findViewById(R.id.img_minus);
        ImageView btnPlus = findViewById(R.id.img_plus);
        ImageView btnCart = findViewById(R.id.img_cart_icon);

        //  استقبال البيانات المرسلة من صفحة الـ Home
        String name = getIntent().getStringExtra("foodName");
        String price = getIntent().getStringExtra("foodPrice");
        int imageId = getIntent().getIntExtra("foodImage", 0);

        //  عرض البيانات المستلمة في الصفحة
        foodName.setText(name);
        foodPrice.setText(price);
        foodImage.setImageResource(imageId);

        //  برمجة زر الزائد (+)
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                quantityText.setText(String.valueOf(count));
            }
        });

        //  برمجة زر الناقص (-)
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 1) {
                    count--;
                    quantityText.setText(String.valueOf(count));
                }
            }
        });

        //  برمجة زر السلة (تخزين البيانات فعلياً)
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // إنشاء غرض من الوجبة المختارة
                Food selectedFood = new Food(name, price, imageId);

                // إضافة الوجبة للمخزن بعدد المرات (count)
                for (int i = 0; i < count; i++) {
                    Food.cartList.add(selectedFood);
                }

                // رسالة تأكيد للمستخدم
                Toast.makeText(FoodDetailsActivity.this,
                        "تمت إضافة " + count + " من " + name + " للسلة",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}