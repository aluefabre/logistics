package org.fabrelab.textkit.zhidao;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.fabrelab.pagekit.CrawlerException;
import org.fabrelab.pagekit.baiduzhidao.BaiduZhidaoCrawler;

public class CrawlerButtons {

	public static void createKeywordText(Group parent) { 
		final Text keywordText = new Text(parent, SWT.BORDER | SWT.WRAP);
		keywordText.setText(ZhidaoTool.keyword);
		GridData data = new GridData(GridData.CENTER, GridData.CENTER, true, true);
		data.widthHint = 200;
		keywordText.setLayoutData(data);
		keywordText.addKeyListener(new KeyListener() {
			
			public void keyReleased(KeyEvent paramKeyEvent) {
				ZhidaoTool.keyword = keywordText.getText();
			}
			
			public void keyPressed(KeyEvent paramKeyEvent) {}
		});
	}
	
	public static void createRunButton(Group current) {
		final Button button1 = new Button(current, SWT.PUSH);
		button1.setText("  >>  ");
		GridData data = new GridData(GridData.BEGINNING, GridData.FILL, true, true);
		button1.setLayoutData(data);
		button1.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			public void widgetSelected(SelectionEvent arg0) {
				try {
					boolean running = BaiduZhidaoCrawler.run(ZhidaoTool.keyword, ZhidaoTool.processor, 1000);
					if (running) {
						button1.setText("  X  ");
					} else {
						button1.setText("  >> ");
					}
				} catch (CrawlerException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void createPauseButton(Group current) {

		final Button button1 = new Button(current, SWT.PUSH);
		button1.setText("  ||  ");
		GridData data = new GridData(GridData.BEGINNING, GridData.FILL, true, true);
		button1.setLayoutData(data);
		button1.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
			public void widgetSelected(SelectionEvent arg0) {
				try {
					boolean pause = BaiduZhidaoCrawler.pause();
					if (pause) {
						button1.setText("  ->  ");
					} else {
						button1.setText("  ||  ");
					}
				} catch (CrawlerException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
