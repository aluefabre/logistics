package org.fabrelab.textkit.zhidao;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.fabrelab.pagekit.CrawlerException;
import org.fabrelab.pagekit.baiduzhidao.BaiduZhidaoCrawler;
import org.fabrelab.pagekit.baiduzhidao.model.BaiduZhidaoQuestion;
import org.fabrelab.textkit.zhidao.baidu.BaiduAnswerUtil;
import org.fabrelab.textkit.zhidao.processor.ToolProcessor;

public class ZhidaoTool {

	static String keyword = "单号";
	static String user = "aluefabre";
	static String password = "";
	
	static ToolProcessor processor = new ToolProcessor();


	public static List<BaiduZhidaoQuestion> questions = new ArrayList<BaiduZhidaoQuestion>();
	
	static Text totalNumberText;

	static Display display = new Display();

	public static void main(String[] args) throws CrawlerException {

		Shell shell = new Shell(Display.getCurrent());
		shell.setText("Baidu Zhidao Question Answer Robot");
		GridLayout gridLayout = new GridLayout(1, false);
		shell.setLayout(gridLayout);
		createHeadGroup(shell);
		createItemGroup(shell);
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		BaiduZhidaoCrawler.stop();
	}

	private static void createHeadGroup(Shell shell) {
		Group current = new Group(shell, SWT.NONE);
		current.setText("head");
		GridData data = new GridData(GridData.FILL, GridData.CENTER, true, false);
		current.setLayoutData(data);
		current.setLayout(new GridLayout(3, false));
		createCrawlerGroup(current);
		createNumberGroup(current);
		createControlGroup(current);
	}

	private static void createCrawlerGroup(Group parent) {
		Group current = new Group(parent, SWT.NONE);
		current.setText("crawler");
		GridData data = new GridData(GridData.BEGINNING, GridData.CENTER, true, false);
		data.widthHint = 200;
		current.setLayoutData(data);
		current.setLayout(new GridLayout(2, true));
		CrawlerButtons.createKeywordText(current);
		CrawlerButtons.createRunButton(current);
		//CrawlerButtons.createPauseButton(current);
	}


	private static void createNumberGroup(Group parent) {
		Group current = new Group(parent, SWT.NONE);
		current.setText("number");
		GridData data = new GridData(GridData.BEGINNING, GridData.CENTER, true, false);
		data.widthHint = 300;
		current.setLayoutData(data);
		current.setLayout(new GridLayout(3, true));
		createNumberText(current);
	}

	private static void createNumberText(Group parent) {
		totalNumberText = new Text(parent, SWT.BORDER | SWT.WRAP);
		totalNumberText.setText("0");
		Font initialFont = totalNumberText.getFont();
		FontData[] fontData = initialFont.getFontData();
		for (int i = 0; i < fontData.length; i++) {
			fontData[i].setHeight(12);
		}
		Font newFont = new Font(parent.getDisplay(), fontData);
		totalNumberText.setFont(newFont);
		GridData data = new GridData(GridData.CENTER, GridData.CENTER, true, true);
		data.widthHint = 100;
		data.heightHint = 25;
		totalNumberText.setLayoutData(data);
		totalNumberText.setEditable(false);
	}

	public static void updateData() {
		display.syncExec(new Runnable() {
			public void run() {
				totalNumberText.setText(questions.size() + "");
				QuestionTable.update(0);
			}
		});
	}

	private static void createControlGroup(Group parent) {
		Group current = new Group(parent, SWT.NONE);
		current.setText("answer");
		GridData data = new GridData(GridData.END, GridData.CENTER, true, false);
		data.widthHint = 300;
		current.setLayoutData(data);
		current.setLayout(new GridLayout(3, true));
		createUserText(current);
		createPasswordText(current);
		createAnswerButton(current);
	}

	private static void createPasswordText(Group parent) {
		final Text keywordText = new Text(parent, SWT.BORDER | SWT.WRAP);
		keywordText.setText("");
		GridData data = new GridData(GridData.CENTER, GridData.CENTER, true, true);
		data.widthHint = 200;
		keywordText.setLayoutData(data);
		keywordText.addKeyListener(new KeyListener() {
			
			public void keyReleased(KeyEvent paramKeyEvent) {
				ZhidaoTool.password = keywordText.getText();
			}
			
			public void keyPressed(KeyEvent paramKeyEvent) {}
		});
	}

	private static void createUserText(Group parent) {
		final Text keywordText = new Text(parent, SWT.BORDER | SWT.WRAP);
		keywordText.setText("aluefabre");
		GridData data = new GridData(GridData.CENTER, GridData.CENTER, true, true);
		data.widthHint = 200;
		keywordText.setLayoutData(data);
		keywordText.addKeyListener(new KeyListener() {
			
			public void keyReleased(KeyEvent paramKeyEvent) {
				ZhidaoTool.user = keywordText.getText();
			}
			
			public void keyPressed(KeyEvent paramKeyEvent) {}
		});
	}

	private static void createAnswerButton(Group current) {
		Button button1 = new Button(current, SWT.PUSH);
		button1.setText("Answer");
		GridData data = new GridData(GridData.END, GridData.FILL, true, true);
		button1.setLayoutData(data);
		button1.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {

			}

			public void widgetSelected(SelectionEvent arg0) {
				ISelection selection = QuestionTable.tv.getSelection();
				if(selection!=null){
					StructuredSelection ss = (StructuredSelection)selection;
					BaiduZhidaoQuestion question = (BaiduZhidaoQuestion)ss.getFirstElement();
					if(question!=null){
						boolean success = BaiduAnswerUtil.answer(user, password, question);
						if(success){
							question.setAnswered(true);
							updateData();
						}
					}
				}
			}
		});
	}

	private static void createItemGroup(Shell shell) {
		Group current = new Group(shell, SWT.NONE);
		current.setText("questions");
		GridData data = new GridData(GridData.FILL, GridData.FILL, true, true);
		data.widthHint = 900;
		data.heightHint = 500;
		current.setLayoutData(data);
		current.setLayout(new GridLayout(2, false));
		QuestionTable.createContents(current);
	}


}