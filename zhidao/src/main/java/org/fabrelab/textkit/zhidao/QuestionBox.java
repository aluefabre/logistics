package org.fabrelab.textkit.zhidao;

//Send questions, comments, bug reports, etc. to the authors:

//Rob Warner (rwarner@interspatial.com)
//Robert Harris (rbrt_harris@yahoo.com)

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.fabrelab.pagekit.baiduzhidao.model.BaiduZhidaoQuestion;

public class QuestionBox {
	
	BaiduZhidaoQuestion question;

	public QuestionBox(BaiduZhidaoQuestion question) {
		this.question = question;
	}

	/**
	 * Runs the application
	 */
	public void run() {
		final Shell dialog = new Shell(ZhidaoTool.display, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		dialog.setText("Question Detail");
		dialog.setSize(800, 600);
		createContents(dialog);
		dialog.open();
	}

	/**
	 * Creates the main window's contents
	 * 
	 * @param shell
	 *            the parent shell
	 */
	private void createContents(final Shell dialog) {
		dialog.setLayout(new GridLayout(2, false));

		addTextRow(dialog, "URL:", question.getUrl());

		addTextRow(dialog, "Title:", question.getTitle());

		addTextRow(dialog, "Content:", question.getContent());

		addTextRow(dialog, "Type:", question.getType());

		addTextRow(dialog, "AnalysisResult:", question.getAnalysisResult());

		addTextRow(dialog, "ExtractedText:", question.getExtractedText());

		addTextRow(dialog, "Answer:", question.getAnswer());
	}

	private void addTextRow(final Shell dialog, String labelName, String textVal) {
		new Label(dialog, SWT.NONE).setText(labelName+"");
		final Text message = new Text(dialog, SWT.MULTI|SWT.WRAP);
		message.setLayoutData(new GridData(GridData.FILL_BOTH));
		message.setText(textVal+"");
	}


}
