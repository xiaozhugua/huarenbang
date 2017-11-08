package com.abcs.haiwaigou.view.TabLayout;

import com.abcs.haiwaigou.local.beans.FiletersBean;
import com.abcs.haiwaigou.local.beans.TagsBean;

import java.util.List;

/**
 * Created by HanHailong on 15/10/20.
 */
public interface OnTagSelectListenerBean {
    void onItemSelect(FlowTagLayout parent, List<FiletersBean> selectedList);

    void onItemSelect2(FlowTagLayout parent, List<TagsBean> selectedList);
}
