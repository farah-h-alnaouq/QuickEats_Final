package com.example.quickeats;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CartActivity extends AppCompatActivity {

    ListView listView;
    TextView tvTotal;
    FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //  إعداد التولبار وسهم الرجوع
        Toolbar toolbar = findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        //  ربط العناصر بالـ XML
        listView = findViewById(R.id.cart_list);
        tvTotal = findViewById(R.id.tv_total_price);

        //  تجهيز الأدابتر وربطه بالـ ListView
        adapter = new FoodAdapter(this, Food.cartList);
        listView.setAdapter(adapter);

        //  برمجة زر إتمام الطلب (المعدل)
        Button btnCheckout = findViewById(R.id.btn_checkout);
        btnCheckout.setOnClickListener(v -> {
            if (Food.cartList.isEmpty()) {
                Toast.makeText(this, "السلة فارغة، أضف بعض الطعام أولاً!", Toast.LENGTH_SHORT).show();
            } else {
                // الانتقال لواجهة النجاح
                Intent intent = new Intent(CartActivity.this, OrderSuccessActivity.class);
                startActivity(intent);

                // تنظيف السلة وتحديث الواجهة
                Food.cartList.clear();
                adapter.notifyDataSetChanged();
                tvTotal.setText("0 $");

                // إغلاق صفحة السلة بعد الانتقال
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged(); // تحديث القائمة بالوجبات الجديدة
            calculateTotal(); // إعادة حساب السعر
        }
    }

    // دالة حساب السعر
    private void calculateTotal() {
        int total = 0;
        for (Food f : Food.cartList) {
            try {
                // استخراج الأرقام فقط من نص السعر (مثلاً "15 $" تصبح 15)
                String priceValue = f.getPrice().replaceAll("[^0-9]", "");
                total += Integer.parseInt(priceValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        tvTotal.setText(total + " $");
    }
}