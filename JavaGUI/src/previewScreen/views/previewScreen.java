package previewScreen.views;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.JTextPane;

public class previewScreen extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable infoTabel;
	private JTable cameraTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					previewScreen frame = new previewScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws JSONException 
	 * @throws FileNotFoundException 
	 */
	public previewScreen() throws FileNotFoundException, JSONException {
		setResizable(false);
		getContentPane().setLayout(null);
		
		JLabel infoLabel = new JLabel("Info:");
		infoLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		infoLabel.setBounds(10, 11, 46, 14);
		getContentPane().add(infoLabel);
		
		JLabel cameraSettings = new JLabel("Camera Settings:");
		cameraSettings.setFont(new Font("Tahoma", Font.BOLD, 13));
		cameraSettings.setBounds(10, 90, 118, 14);
		getContentPane().add(cameraSettings);
		
		JButton editCamera = new JButton("Edit");
		editCamera.setBounds(225, 153, 89, 23);
		getContentPane().add(editCamera);
		
		JButton editInfo = new JButton("Edit");
		editInfo.setBounds(225, 48, 89, 23);
		getContentPane().add(editInfo);
		
		initComponents();
	}
	private void initComponents() throws FileNotFoundException, JSONException {
		setTitle("Preview Settings");
		
		JSONObject settings = readJSON("settings");
		JSONObject info = settings.getJSONObject("info");
		JSONObject camera = settings.getJSONObject("camera");
		
		
		createInfoTable(info);
		
		createCameraTable(camera);

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 330, 299);;
	}


	private void createCameraTable(JSONObject camera) throws JSONException {
		DefaultTableModel cameraModel = new DefaultTableModel();
		cameraModel.addColumn("Camera");
		cameraModel.addColumn("Value");
		for (int i = 0; i < camera.names().length(); i++) {
			String name = new String(camera.names().get(i).toString());
			String value = new String(camera.get(name).toString());
			if (name == "binning") {
				System.out.println("Hello");
				value = camera.get(name)+"x"+camera.get(name);
			}
			else {
				
			}
			cameraModel.insertRow(0, new Object[] { name.toUpperCase(), value });
		}
		cameraTable = new JTable(cameraModel);
		cameraTable.setBounds(10, 115, 205, 144);
		getContentPane().add(cameraTable);
				
	}

	private void createInfoTable(JSONObject info) throws JSONException {
		DefaultTableModel infoModel = new DefaultTableModel();
		infoModel.addColumn("Info");
		infoModel.addColumn("Value");
		for (int i = 0; i < info.names().length(); i++) {
			String name = new String(info.names().get(i).toString());
		    infoModel.insertRow(0, new Object[] { name.toUpperCase(),info.get(name) });
		}
		infoTabel = new JTable(infoModel);
		infoTabel.setBounds(10, 31, 205, 50);
		getContentPane().add(infoTabel);
		
	}

	protected void writeJSON(JSONObject o, String file) throws IOException {
		FileWriter f = new FileWriter(file+".json");
		f.write(o.toString());
		f.flush();
		f.close();
	}

	protected JSONObject readJSON(String file) throws FileNotFoundException, JSONException {
		FileReader r = new FileReader(file+".json");
		JSONTokener t = new JSONTokener(r);
		JSONObject obj = new JSONObject(t);
		return obj;
	}
}
