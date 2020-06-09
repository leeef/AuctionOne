package com.hlnwl.auction.ui.home;

import android.content.Context;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.QuestionListBean;
import com.hlnwl.auction.bean.QuestionnairBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 问卷调查
 */
public class QuestionnaireAdapter extends BaseQuickAdapter<QuestionListBean.DataBean, BaseViewHolder> {
    private Context mContext;

    public static Map<Integer,String> map = new HashMap<>();

    public QuestionnaireAdapter(Context context) {
        super(R.layout.item_questionnaire);
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final QuestionListBean.DataBean item) {
        helper.setText(R.id.tv_content,(helper.getAdapterPosition()+1)+"、"+item.getTitle());

        RadioGroup radioGroup = helper.getView(R.id.radio);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_yes:
                        item.setCheck(1);

                        map.put(helper.getAdapterPosition(),"");
                        break;
                    case R.id.rb_no:
                        item.setCheck(0);
                        map.put(helper.getAdapterPosition(),"");
                        break;

                    default:
                        break;

                }
            }
        });

    }

    public Map<Integer, String> getMap() {
        return map;
    }
}
