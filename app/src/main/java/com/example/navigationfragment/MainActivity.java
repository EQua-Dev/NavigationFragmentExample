package com.example.navigationfragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        we inform the app that we intend to use our toolbar as our action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.draw_layout);
//        we set click listener to items in our navigation drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        this is to implement the hamburger icon for the drawer menu
//        ActionDrawerToggle is a handy class that handles the creation of the hamburger icon and its functionality
//        we pass in the drawer and toolbar variable because they are the two views that will be connected and synced
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
//                these two strings are not actually visible on the screen but is an aid for screen speech (helpful for blind people e.g)
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        this will take care of rotating the hamburger icon as well as the drawer itself
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        we ensure that the default fragment and selected item is called only when the app is launched
        if (savedInstanceState == null) {
//        we set one of the fragments to be open at default in order to avoid an empty screen on start
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MessageFragment()).commit();
//        we also set the default item selected to be one of the items
            navigationView.setCheckedItem(R.id.nav_message);
        }
    }

    @Override
    public void onBackPressed() {
//        if the back button is pressed and the navigation drawer is open, it closes the drawer as opposed to closing the activity
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
//            if the back button is pressed and the navigation drawer is not open, the activity is closed
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        we create a switch statement for when each item in clicked
        switch (menuItem.getItemId()){
//            we create cases for each of our desired clickable items and replace the fragment_container with the fragment of the clicked item activity
            case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MessageFragment()).commit();
//                break statement is used to prevent the next case from running when the previous case has been satisfied
                break;

            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChatFragment()).commit();
//                break statement is used to prevent the next case from running when the previous case has been satisfied
                break;

            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
//                break statement is used to prevent the next case from running when the previous case has been satisfied
                break;

            case R.id.nav_share:
                Intent intent = null, chooser = null;
                intent = new Intent(Intent.ACTION_SEND);
                String message = "THis is what I want to share";
//            the predefined key 'EXTRA_TEXT' is one of the keys recognizable by other applications
                intent.putExtra(Intent.EXTRA_TEXT, message);
                intent.setType("text/plain");
                chooser = Intent.createChooser(intent, "Share to: ");
                startActivity(chooser);
                break;


            case R.id.nav_send:
                Intent sendIntent = null, sendChooser = null;
                sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setData(Uri.parse("mail to: "));
                String [] to = {"basseyemmanuel23@gmail.com","Josephofudi@gmail.com", "luomyequa@gmail.com"};
                sendIntent.putExtra(Intent.EXTRA_EMAIL,to);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "This is a drill mail for testing purpose only");
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Good day this is just an experimental email sent from my app, rely at will.\n It is not a hacksploit, it is not a Spam.\n Thank you");
                sendIntent.setType("message/rfc822");
                sendChooser= Intent.createChooser(sendIntent,"Send Mail");
                startActivity(sendChooser);
                break;
        }
//        we set our navigation drawer to to close when an item is selected
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}
