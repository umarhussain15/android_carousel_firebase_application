package carousel.teamo.com.carouselapplication.draweractivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import carousel.teamo.com.carouselapplication.AddNewGameDialog;
import carousel.teamo.com.carouselapplication.LaunchActivity;
import carousel.teamo.com.carouselapplication.MyRecycler;
import carousel.teamo.com.carouselapplication.R;
import carousel.teamo.com.carouselapplication.RecyclerHelperPOJO;
import carousel.teamo.com.carouselapplication.ScorePOJO;

public class AddScoreActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AddNewGameDialog.EditNameDialogListener {
    private static final String GAMES_LIST = "game_list";
    private DatabaseReference mDatabase;
    RecyclerView recyclerView;
    MyRecycler myRecycler;
    String userId;
    List<RecyclerHelperPOJO> recyclerHelperPOJOs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView= (RecyclerView)findViewById(R.id.list);
        userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerHelperPOJOs= new ArrayList<>();
        myRecycler= new MyRecycler(recyclerHelperPOJOs,getApplicationContext());
        recyclerView.setAdapter(myRecycler);
        mDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Log.d("ADDSCOREACTIVITY",dataSnapshot.toString());
                  Iterable<DataSnapshot> iterable= dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator=iterable.iterator();
                    recyclerHelperPOJOs.clear();
//                    recyclerView.removeAllViews();
//                    myRecycler.notifyite
                    while (iterator.hasNext()){
                        DataSnapshot s=iterator.next();
                        ScorePOJO scorePOJO= s.getValue(ScorePOJO.class);
                        RecyclerHelperPOJO  recyclerHelperPOJO= new RecyclerHelperPOJO(s.getKey(),scorePOJO);
//                        recyclerHelperPOJOs.remove(recyclerHelperPOJO);
                        recyclerHelperPOJOs.add(recyclerHelperPOJO);
                    }
//                    myRecycler= new MyRecycler(recyclerHelperPOJOs,getApplicationContext());
//                    recyclerView.setAdapter(myRecycler);
////                    Log.d("ADDSCOREACTIVITY", Arrays.toString(recyclerHelperPOJOs.toArray()));
//                    recyclerView.invalidate();
                    myRecycler.notifyDataSetChanged();
                }
                else{
                    recyclerHelperPOJOs.clear();
                    myRecycler.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_reset) {
            String uId=FirebaseAuth.getInstance().getCurrentUser().getUid();
            mDatabase.child(uId).removeValue();
        } else if (id == R.id.nav_logout) {
            LoginManager.getInstance().logOut();
            startActivity(new Intent(this, LaunchActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showAlertDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddNewGameDialog alertDialog = AddNewGameDialog.newInstance("Add New Game");
        alertDialog.show(fm, "fragment_alert");
    }


    @Override
    public void onFinishEditDialog(String inputText) {
        String uId=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.child(uId).push().setValue(new ScorePOJO(inputText,0));
    }
}
