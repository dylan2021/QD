/*
 * Copyright (c) 2015 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haocang.base.adapter;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.haocang.base.R;
import com.haocang.base.bean.Contact;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.widget.AdapterHolder;
import org.kymjs.kjframe.widget.KJAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 列表适配器
 *
 * @author kymjs (http://www.kymjs.com/) on 9/16/15.
 */
public class ContactAdapter extends KJAdapter<Contact> implements SectionIndexer {

    private KJBitmap kjb = new KJBitmap();
    private List<Contact> datas;
    private char firstChar = '#';
    private int index = -1;

    public ContactAdapter(AbsListView view, List<Contact> mDatas) {
        super(view, mDatas, R.layout.item_list_contact);
        datas = mDatas;
        if (datas == null) {
            datas = new ArrayList<>();
        }
        Collections.sort(datas);
    }

    @Override
    public void convert(AdapterHolder helper, Contact item, boolean isScrolling) {
    }


    @Override
    public void convert(AdapterHolder holder, Contact item, boolean isScrolling, int position) {

        ImageView selectIv = holder.getView(R.id.select_iv);
        TextView tvLetter = holder.getView(R.id.contact_catalog);
        TextView tvLine = holder.getView(R.id.contact_line);
        //如果是第0个那么一定显示#号
//        if (position == 0) {
//            tvLetter.setVisibility(View.VISIBLE);
//            tvLetter.setText("#");
//            tvLine.setVisibility(View.VISIBLE);
//        } else {

        //如果和上一个item的首字母不同，则认为是新分类的开始
//            Contact prevData = datas.get(position - 1);
        if (item.isSelect()) {
            selectIv.setVisibility(View.VISIBLE);
        } else {
            selectIv.setVisibility(View.GONE);
        }
        if (index == position) {
            return;
        }
        if (item.getFirstChar() != firstChar) {
            tvLetter.setVisibility(View.VISIBLE);
            tvLetter.setText("" + item.getFirstChar());
            tvLine.setVisibility(View.VISIBLE);
        } else {
            tvLetter.setVisibility(View.GONE);
            tvLine.setVisibility(View.GONE);
        }

        holder.setText(R.id.contact_title, item.getName());
        firstChar = item.getFirstChar();
        index = position;
//        }
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        Contact item = datas.get(position);
        return item.getFirstChar();
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            char firstChar = datas.get(i).getFirstChar();
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    public ArrayList<Contact> getDatas() {
        ArrayList<Contact> list = new ArrayList<>();
        for (Contact contact : datas) {
            if (contact.isSelect()) {
                list.add(contact);
            }
        }
        return list;
    }
}
