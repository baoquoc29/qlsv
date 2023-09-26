package com.example.qlsv;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        Toolbar toolbar = new Toolbar(MainActivity.this);
        db = new SqliteHelper(MainActivity.this);
        list = db.display_view();
        list_clone = db.display_view();
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_expandable_list_item_1, list);
        listView.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String get_id = id.getText().toString();
                String get_name = name.getText().toString();
                String get_major = major.getText().toString();
                String class_st = classst.getText().toString();
                long newRowId = db.insertData(get_id, get_name, class_st, get_major);
                if (newRowId != -1) {
                    list.add(new Students(get_id, get_name, class_st, get_major));
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    id.setText("");
                    name.setText("");
                    major.setText("");
                    classst.setText("");

                } else {
                    Toast.makeText(MainActivity.this, "Lỗi khi thêm dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Students get_list = list.get(i);
                String values = String.valueOf(get_list);
                String [] array = values.split(" ");
                id.setText(array[0]);
                classst.setText(array[2]);
                major.setText(array[3]);
                name.setText(array[1]);
                index = i;
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_remove = id.getText().toString();
                db.deleteColumns(id_remove);
                    if(list.get(index).getStudentId().equals(id_remove)){
                        list.remove(index);

                }
                adapter.notifyDataSetChanged();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_id = id.getText().toString();
                String get_name = name.getText().toString();;
                String class_st = classst.getText().toString();
                String get_major = major.getText().toString();
                db.updateColumn(get_id,get_name,class_st,get_major);
                list.set(index,new Students(get_id, get_name, class_st, get_major));
                adapter.notifyDataSetChanged();
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }
    public void filter(String text){
        List<Students> list2 = new ArrayList<>();
        for(Students students : list){
            if(students.getStudentId().contains(text)){
                list2.add(students);
            }
        }
        if(text.isEmpty()){
            adapter.clear();
            adapter.addAll(list_clone);
            adapter.notifyDataSetChanged();
        }
        else{
            adapter.clear();
            adapter.addAll(list2);
            adapter.notifyDataSetChanged();
        }
    }
    public void mapping(){
        id = findViewById(R.id.bookID);
        name = findViewById(R.id.StudentName);
        classst = findViewById(R.id.Classst);
        major = findViewById(R.id.Major);
        btn = findViewById(R.id.clickAdd);
        listView = findViewById(R.id.listView);
        edit = findViewById(R.id.clickEdit);
        remove = findViewById(R.id.clickRemove);
        list_clone = list;
        search = findViewById(R.id.search);
    }
    private EditText search;
    private Button remove;
    private Button edit;
    private EditText id;
    private EditText name;
    private EditText classst;
    private EditText major;
    private Button btn;
    private ListView listView;
    private SqliteHelper db;
    private ArrayAdapter<Students> adapter;
    private   int index;
    private List<Students> list = new ArrayList<>();;
    private List<Students> list_clone = new ArrayList<>();


}