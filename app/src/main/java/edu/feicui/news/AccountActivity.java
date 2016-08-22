package edu.feicui.news;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;

import base.MyBaseActivity;
import fragment.AccountFragment;

public class AccountActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        this.getSupportFragmentManager().beginTransaction()
                .add(R.id.lyt_account,new AccountFragment()).commit();
    }

    @Override
    public void handleMsg(Message msg) {

    }
}
