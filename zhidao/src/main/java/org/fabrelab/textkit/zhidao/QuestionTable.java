package org.fabrelab.textkit.zhidao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.fabrelab.pagekit.baiduzhidao.model.BaiduZhidaoQuestion;

/**
 * This class demonstrates TableViewers
 */
public class QuestionTable {
	// The table viewer
	public static TableViewer tv;

	public static Control createContents(Composite parent) {
		// Create the composite to hold the controls
		Composite composite = new Composite(parent, SWT.NONE);

		composite.setLayout(new GridLayout(1, false));

		GridData data = new GridData(GridData.FILL, GridData.FILL, true, true);

		composite.setLayoutData(data);

		// Create the table viewer to display the players
		tv = new TableViewer(composite, SWT.FULL_SELECTION);

		// Set the content and label providers
		tv.setContentProvider(new QuestionContentProvider());
		tv.setLabelProvider(new PlayerLabelProvider());

		// Set up the table
		final Table table = tv.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Add the first name column
		TableColumn tc = new TableColumn(table, SWT.LEFT);
		tc.setText("URL");
		tc.setWidth(50);

		// Add the last name column
		tc = new TableColumn(table, SWT.LEFT);
		tc.setText("Title");
		tc.setWidth(150);

		// Add the points column
		tc = new TableColumn(table, SWT.LEFT);
		tc.setText("Content");
		tc.setWidth(300);

		// Add the rebounds column
		tc = new TableColumn(table, SWT.LEFT);
		tc.setText("Time");
		tc.setWidth(100);

		// Add the assists column
		tc = new TableColumn(table, SWT.LEFT);
		tc.setText("Answered");
		tc.setWidth(90);

		// Add the assists column
		tc = new TableColumn(table, SWT.LEFT);
		tc.setText("Type");
		tc.setWidth(90);

		tc = new TableColumn(table, SWT.LEFT);
		tc.setText("AnalysisResult");
		tc.setWidth(200);

		tc = new TableColumn(table, SWT.LEFT);
		tc.setText("ExtractedText");
		tc.setWidth(100);

		tc = new TableColumn(table, SWT.LEFT);
		tc.setText("Answer");
		tc.setWidth(100);
		
		update(0);

		// Turn on the header and the lines
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		table.addListener(SWT.MouseDoubleClick, new Listener() {
			public void handleEvent(Event event) {
				Point pt = new Point(event.x, event.y);
				final TableItem item = table.getItem(pt);
				if (item == null)
					return;
				int columnIndex = 0;
				for (int i = 0; i < table.getColumnCount(); i++) {
					Rectangle rect = item.getBounds(i);
					if (rect.contains(pt)) {
						columnIndex = i;
						break;
					}
				}
				if (columnIndex == 0) {
					org.eclipse.swt.program.Program.launch(item.getText());
				}else{
					ZhidaoTool.display.syncExec(new Runnable() {
						public void run() {
							new QuestionBox((BaiduZhidaoQuestion) item.getData()).run();
						}
					});
				}
			}
		});

		return composite;
	}

	/**
	 * Updates the application with the selected team
	 * 
	 * @param team
	 *            the team
	 */
	public static void update(int selection) {
		tv.setInput(ZhidaoTool.questions);
	}

}

/**
 * This class contains constants for the PlayerTable application
 */

class PlayerConst {
	// Column constants
	public static final int COLUMN_URL_NAME = 0;

	public static final int COLUMN_TITLE_NAME = 1;

	public static final int COLUMN_CONTENT = 2;

	public static final int COLUMN_TIME = 3;

	public static final int COLUMN_ANSWERED = 4;

	public static final int COLUMN_TYPE = 5;

	public static final int COLUMN_ANALYSIS_RESULT = 6;

	public static final int COLUMN_EXTRACTED_TEXT = 7;

	public static final int COLUMN_ANSWER = 8;
}

/**
 * This class provides the labels for PlayerTable
 */

class PlayerLabelProvider implements ITableLabelProvider {
	// Image to display if the player led his team
	private Image ball;

	// Constructs a PlayerLabelProvider
	public PlayerLabelProvider() {
		// Create the image
		try {
			ball = new Image(null, new FileInputStream("images/ball.png"));
		} catch (FileNotFoundException e) {
			// Swallow it
		}
	}
	
	public Image getColumnImage(Object arg0, int arg1) {
		return ball;
	}

	public String getColumnText(Object arg0, int arg1) {
		BaiduZhidaoQuestion player = (BaiduZhidaoQuestion) arg0;
		String text = "";
		switch (arg1) {
		case PlayerConst.COLUMN_URL_NAME:
			text = player.getUrl();
			break;
		case PlayerConst.COLUMN_TITLE_NAME:
			text = player.getTitle();
			break;
		case PlayerConst.COLUMN_CONTENT:
			text = player.getContent();
			break;
		case PlayerConst.COLUMN_TIME:
			text = player.getTime();
			break;
		case PlayerConst.COLUMN_ANSWERED:
			text = player.isAnswered() + "";
			break;
		case PlayerConst.COLUMN_TYPE:
			text = player.getType() + "";
			break;
		case PlayerConst.COLUMN_ANALYSIS_RESULT:
			text = player.getAnalysisResult() + "";
			break;
		case PlayerConst.COLUMN_EXTRACTED_TEXT:
			text = player.getExtractedText() + "";
			break;
		case PlayerConst.COLUMN_ANSWER:
			text = player.getAnswer() + "";
			break;	
		}

		return text;
	}

	/**
	 * Adds a listener
	 * 
	 * @param arg0
	 *            the listener
	 */
	public void addListener(ILabelProviderListener arg0) {
		// Throw it away
	}

	/**
	 * Dispose any created resources
	 */
	public void dispose() {
		// Dispose the image
		if (ball != null)
			ball.dispose();
	}

	/**
	 * Returns whether the specified property, if changed, would affect the
	 * label
	 * 
	 * @param arg0
	 *            the player
	 * @param arg1
	 *            the property
	 * @return boolean
	 */
	public boolean isLabelProperty(Object arg0, String arg1) {
		return false;
	}

	/**
	 * Removes the specified listener
	 * 
	 * @param arg0
	 *            the listener
	 */
	public void removeListener(ILabelProviderListener arg0) {
		// Do nothing
	}
}

/**
 * This class provides the content for the table
 */

class QuestionContentProvider implements IStructuredContentProvider {

	public Object[] getElements(Object arg0) {
		// Returns all the players in the specified team
		return ((List<BaiduZhidaoQuestion>) arg0).toArray();
	}

	/**
	 * Disposes any resources
	 */
	public void dispose() {
		// We don't create any resources, so we don't dispose any
	}

	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// Nothing to do
	}
}