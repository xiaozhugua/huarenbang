package com.abcs.imkfsdk.chat.chatrow;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.abcs.imkfsdk.chat.ChatActivity;
import com.abcs.imkfsdk.chat.holder.BaseHolder;
import com.abcs.imkfsdk.chat.holder.TextViewHolder;
import com.abcs.imkfsdk.utils.FaceConversionUtil;
import com.abcs.sociax.android.R;
import com.moor.imkf.model.entity.FromToMessage;
import com.moor.imkf.utils.AnimatedGifDrawable;
import com.moor.imkf.utils.AnimatedImageSpan;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by longwei on 2016/3/9.
 */
public class TextRxChatRow extends BaseChatRow {

    private Context context;

    public TextRxChatRow(int type) {
        super(type);
    }

    @Override
    public boolean onCreateRowContextMenu(ContextMenu contextMenu, View targetView, FromToMessage detail) {
        return false;
    }

    @Override
    protected void buildChattingData(Context context, BaseHolder baseHolder, FromToMessage detail, int position) {
        this.context = context;
        TextViewHolder holder = (TextViewHolder) baseHolder;
        FromToMessage message = detail;
        if (message != null) {

            if (message.withDrawStatus) {
                holder.getWithdrawTextView().setVisibility(View.VISIBLE);
                holder.getContainer().setVisibility(View.GONE);
            } else {
                holder.getWithdrawTextView().setVisibility(View.GONE);
                holder.getContainer().setVisibility(View.VISIBLE);

                if (message.showHtml) {
                    // 给对话内容赋值
                    String msg = message.message.replaceAll("\\n","\n");

                    SpannableString spannableString = new SpannableString(msg);
                    Pattern patten = Pattern.compile("\\d+[：]\\w+\\n", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = patten.matcher(spannableString);
                    while (matcher.find()) {
                        String number = matcher.group();
                        int end = matcher.start() + number.length();
                        RobotClickSpan clickSpan = new RobotClickSpan(number, ((ChatActivity)context));
                        spannableString.setSpan(clickSpan, matcher.start(), end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.lite_blue));
                        spannableString.setSpan(colorSpan, matcher.start(), end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }

                    holder.getDescTextView().setText(spannableString);
                    holder.getDescTextView().setMovementMethod(LinkMovementMethod.getInstance());

                } else {
                    SpannableStringBuilder content = handler(holder.getDescTextView(),
                            message.message);
                    SpannableString spannableString = FaceConversionUtil.getInstace()
                            .getExpressionString(context, content + "", holder.getDescTextView());
                    holder.getDescTextView().setText(spannableString);// 给对话内容赋值
                }
            }
        }
    }

    @Override
    public View buildChatView(LayoutInflater inflater, View convertView) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.kf_chat_row_text_rx, null);
            TextViewHolder holder = new TextViewHolder(mRowType);
            convertView.setTag(holder.initBaseHolder(convertView, true));
        }
        return convertView;
    }

    @Override
    public int getChatViewType() {
        return ChatRowType.TEXT_ROW_RECEIVED.ordinal();
    }

    private SpannableStringBuilder handler(final TextView gifTextView,
                                           String content) {
        SpannableStringBuilder sb = new SpannableStringBuilder(content);
        String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        while (m.find()) {
            String tempText = m.group();
            try {
                String num = tempText.substring(
                        "#[face/png/f_static_".length(), tempText.length()
                                - ".png]#".length());
                String gif = "face/gif/f" + num + ".gif";
                /**
                 * 如果open这里不抛异常说明存在gif，则显示对应的gif 否则说明gif找不到，则显示png
                 * */
                InputStream is = context.getAssets().open(gif);
                sb.setSpan(new AnimatedImageSpan(new AnimatedGifDrawable(is,
                                new AnimatedGifDrawable.UpdateListener() {
                                    @Override
                                    public void update() {
                                        gifTextView.postInvalidate();
                                    }
                                })), m.start(), m.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                is.close();
            } catch (Exception e) {
                String png = tempText.substring("#[".length(),
                        tempText.length() - "]#".length());
                try {
                    sb.setSpan(
                            new ImageSpan(context,
                                    BitmapFactory.decodeStream(context
                                            .getAssets().open(png))),
                            m.start(), m.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
        return sb;
    }

    class RobotClickSpan extends ClickableSpan{
        String msg;
        ChatActivity chatActivity;

        public RobotClickSpan(String msg, ChatActivity chatActivity) {
            this.msg = msg;
            this.chatActivity = chatActivity;
        }

        @Override
        public void onClick(View view) {
            String msgStr = "";
            try {
                msgStr = msg.split("：")[0];
            }catch (Exception e){}
            chatActivity.sendTextMsg(msgStr);

        }
    }
}
