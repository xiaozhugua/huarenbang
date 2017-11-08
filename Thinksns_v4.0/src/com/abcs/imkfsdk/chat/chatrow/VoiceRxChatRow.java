package com.abcs.imkfsdk.chat.chatrow;

import android.content.Context;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;

import com.abcs.imkfsdk.chat.ChatActivity;
import com.abcs.imkfsdk.chat.holder.BaseHolder;
import com.abcs.imkfsdk.chat.holder.VoiceViewHolder;
import com.abcs.sociax.android.R;
import com.moor.imkf.http.FileDownLoadListener;
import com.moor.imkf.http.HttpManager;
import com.moor.imkf.model.entity.FromToMessage;

import java.io.File;
import java.util.UUID;

/**
 * Created by longwei on 2016/3/9.
 */
public class VoiceRxChatRow extends BaseChatRow {

    public VoiceRxChatRow(int type) {
        super(type);
    }

    @Override
    public boolean onCreateRowContextMenu(ContextMenu contextMenu, View targetView, FromToMessage detail) {
        return false;
    }

    @Override
    protected void buildChattingData(final Context context, BaseHolder baseHolder, final FromToMessage detail, final int position) {
        System.out.println("bug的位置" + position);
        final VoiceViewHolder holder = (VoiceViewHolder) baseHolder;
        final FromToMessage message = detail;
        if (message != null) {
            if (message.unread != null && message.unread.equals("1")) {
                ((VoiceViewHolder) baseHolder).voiceUnread.setVisibility(View.VISIBLE);
            } else {
                ((VoiceViewHolder) baseHolder).voiceUnread.setVisibility(View.INVISIBLE);
            }
            if (message.filePath == null || message.filePath.equals("")) {
                String dirStr = Environment.getExternalStorageDirectory() + File.separator + "cc/downloadfile/";
                File dir = new File(dirStr);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, "7moor_record_" + UUID.randomUUID() + ".amr");

                if (file.exists()) {
                    file.delete();
                }

                HttpManager.downloadFile(message.message, file, new FileDownLoadListener() {
                    @Override
                    public void onSuccess(File file) {
                        String filePath = file.getAbsolutePath();
                        message.filePath = filePath;
                        VoiceViewHolder.initVoiceRow(holder, detail, position, (ChatActivity) context, true);
                    }

                    @Override
                    public void onFailed() {

                    }

                    @Override
                    public void onProgress(int progress) {

                    }
                });

            } else {
                holder.initVoiceRow(holder, detail, position, (ChatActivity) context, true);
            }
        }
    }

    @Override
    public View buildChatView(LayoutInflater inflater, View convertView) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.kf_chat_row_voice_rx, null);
            VoiceViewHolder holder = new VoiceViewHolder(mRowType);
            convertView.setTag(holder.initBaseHolder(convertView, true));
        }
        return convertView;
    }

    @Override
    public int getChatViewType() {
        return ChatRowType.VOICE_ROW_RECEIVED.ordinal();
    }
}
