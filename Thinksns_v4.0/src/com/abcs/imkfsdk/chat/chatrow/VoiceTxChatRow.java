package com.abcs.imkfsdk.chat.chatrow;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;

import com.abcs.imkfsdk.chat.ChatActivity;
import com.abcs.imkfsdk.chat.holder.BaseHolder;
import com.abcs.imkfsdk.chat.holder.VoiceViewHolder;
import com.abcs.sociax.android.R;
import com.moor.imkf.model.entity.FromToMessage;

/**
 * Created by longwei on 2016/3/9.
 */
public class VoiceTxChatRow extends BaseChatRow {

    public VoiceTxChatRow(int type) {
        super(type);
    }

    @Override
    public boolean onCreateRowContextMenu(ContextMenu contextMenu, View targetView, FromToMessage detail) {
        return false;
    }

    @Override
    protected void buildChattingData(Context context, BaseHolder baseHolder, FromToMessage detail, int position) {
        final VoiceViewHolder holder = (VoiceViewHolder) baseHolder;
        final FromToMessage message = detail;

        if(message != null) {

            VoiceViewHolder.initVoiceRow(holder, detail, position, (ChatActivity) context, false);

            View.OnClickListener listener = ((ChatActivity)context).getChatAdapter().getOnClickListener();
            getMsgStateResId(position, holder, message, listener);
        }
    }

    @Override
    public View buildChatView(LayoutInflater inflater, View convertView) {

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.kf_chat_row_voice_tx, null);
            VoiceViewHolder holder = new VoiceViewHolder(mRowType);
            convertView.setTag(holder.initBaseHolder(convertView, false));
        }

        return convertView;
    }

    @Override
    public int getChatViewType() {
        return ChatRowType.VOICE_ROW_TRANSMIT.ordinal();
    }
}
