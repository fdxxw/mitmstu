package com.github.fdxxw.mitmstu.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.fdxxw.mitmstu.R;

public class ConfirmDialog extends Dialog {
	
	private Context context;
	private String title;
	private String content;
	private String confirmButtonText;
	private String cancelButtonText;
	private ClickListenerInterface clickListenerInterface;
	public ConfirmDialog(Context context, String title,
			String confirmButtonText,String content, String cancelButtonText) {
		super(context);
		this.context = context;
		this.title = title;
		this.content = content;
		this.confirmButtonText = confirmButtonText;
		this.cancelButtonText = cancelButtonText;
	}
	public void setClickListener(ClickListenerInterface clickListenerInterface) {
		this.clickListenerInterface = clickListenerInterface;
	}
	
	public interface ClickListenerInterface {
		public void doConfirm();
		public void doCancel();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
	
	public void init() {
		LayoutInflater inflater = LayoutInflater.from(context);
		
		View view = inflater.inflate(R.layout.dialog_confirm, null);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(view, new android.view.ViewGroup.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		TextView tvTitle = (TextView)findViewById(R.id.title);
		TextView tvContent = (TextView)findViewById(R.id.content);
		TextView tvConfirm = (TextView)findViewById(R.id.confirm);
		TextView tvCancel = (TextView)findViewById(R.id.cancel);
		
		tvTitle.setText(title);
		tvContent.setText(content);
		tvConfirm.setText(confirmButtonText);
		tvCancel.setText(cancelButtonText);
		
		tvConfirm.setOnClickListener(new ClickListener());
		tvCancel.setOnClickListener(new ClickListener());
		
		 Window dialogWindow = getWindow();
		 WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		 DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		 lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
		 dialogWindow.setAttributes(lp);
	}
	
	
	private class ClickListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			int id = view.getId();
			switch(id) {
			case R.id.confirm : 
				clickListenerInterface.doConfirm(); 
				break;
			case R.id.cancel : 
				clickListenerInterface.doCancel();
				break;
			}
		}
	} 
}
