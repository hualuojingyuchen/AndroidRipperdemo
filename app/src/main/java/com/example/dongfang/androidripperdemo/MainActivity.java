package com.example.dongfang.androidripperdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.dongfang.androidripperdemo.block.ChangeBlock;
import com.example.dongfang.androidripperdemo.block.InputBlock;
import com.example.dongfang.androidripperdemo.block.ShowBlock;
import com.meituan.android.hplus.ripper.block.BlockManager;
import com.meituan.android.hplus.ripper.block.IBlock;
import com.meituan.android.hplus.ripper.model.WhiteBoard;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private WhiteBoard whiteBoard;
    private BlockManager blockManager;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blockManager = new BlockManager();
        whiteBoard = new WhiteBoard();
        whiteBoard.setTTL(100);
        linearLayoutManager = new LinearLayoutManager();
        linearLayoutManager.setManager(blockManager);

        //
        container = (LinearLayout) findViewById(R.id.linear_container);
        linearLayoutManager.setContainer(container);

        IBlock changeBlock = new ChangeBlock(whiteBoard);
        InputBlock inputBlock = new InputBlock(whiteBoard);
        ShowBlock showBlock = new ShowBlock(whiteBoard);
        //将块放到blockmanager中
        blockManager.addBlock(changeBlock);
        blockManager.addBlock(inputBlock);
        blockManager.addBlock(showBlock);
        bindViewObserver();
        //发送createview的消息
        whiteBoard.notifyCreateView();
    }

    /**
     * 创建view和view更新的观察者
     */
    private void bindViewObserver() {
        //得到创建view的观察者
        whiteBoard.getObservable(WhiteBoard.CREATE_VIEW_KEY,Object.class,null).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.e("TAG...create:","CREATE");
                if (o == null){
                    linearLayoutManager.onCreateView();
                }else if(o instanceof IBlock){
                    IBlock targetBlock = (IBlock) o;
                    linearLayoutManager.onCreateView(targetBlock);
                }
            }
        });
        whiteBoard.getObservable(WhiteBoard.UPDATE_VIEW_KEY,Object.class,null).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.e("TAG...update:","UPDATE");
                if (o == null){
                    linearLayoutManager.onUpdateView();
                }  else if (o instanceof IBlock){
                    IBlock targetBlock = (IBlock) o;
                    linearLayoutManager.onUpdateView(targetBlock);
                }
            }
        });
    }
}
