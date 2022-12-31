import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class PharmacyFrame extends javax.swing.JFrame {
    private String tempId;
    private String fileName;
    private File medecinesFile;
    static int no = 1;
    private ArrayList<String> idss = new ArrayList<String> ();
    private ArrayList<Medecine> meds= new ArrayList<Medecine> ();
    private void fillAList(Scanner s){
        while(s.hasNextLine()){
            this.meds.add(this.analyzeMedecineFromStringFile(s.nextLine()));
        }
    }

    private void firstGoToInsert(){
        this.goToInsert();
        this.searchRaButton.setEnabled(false);
    }
    private void goToInsert(){
        this.idss.removeAll(idss);
        this.meds.removeAll(meds);
        try {

            this.fillAList(new Scanner(medecinesFile));
        } catch (FileNotFoundException ex) {
            //  Logger.getLogger(PharmacyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.saveButton.setEnabled(false);
        this.searchButton.setEnabled(false);
        this.deleteButton.setEnabled(false);
        this.updateButton.setEnabled(false);

        this.idAvaLabel.setVisible(false);

        this.dayField.setEditable(true);
        this.monthField.setEditable(true);
        this.yearField.setEditable(true);
        this.idField.setEditable(true);
        this.nameField.setEditable(true);
        this.priceField.setEditable(true);
        this.unitField.setEditable(true);
        this.qtyField.setEditable(true);

        this.dayField.setText("");
        this.monthField.setText("");
        this.yearField.setText("");
        this.idField.setText("");
        this.nameField.setText("");
        this.priceField.setText("");
        this.unitField.setText("");
        this.qtyField.setText("");

        this.UDRaButton.setEnabled(false);
        this.searchRaButton.setEnabled(true);

        this.idAvaLabel.setVisible(false);
        this.idInvalidLabel.setVisible(true);

        this.insertRaButton.setSelected(true);

    }
    public Medecine analyzeMedecineFromStringFile(String med){
        String [] medData = med.split(", ");
        Medecine crMed = new Medecine();
        for(char c:medData[1].toCharArray()) if(!Character.isDigit(c)) throw new IllegalArgumentException();
        if(idss.contains(medData[1])) throw new NoSuchElementException();
        String [] dateData = medData[3].split("-");
        for(char c:dateData[1].toCharArray()) if(!Character.isDigit(c)) throw new IllegalArgumentException();
        for(char c:dateData[2].toCharArray()) if(!Character.isDigit(c)) throw new IllegalArgumentException();
        for(char c:dateData[0].toCharArray()) if(!Character.isDigit(c)) throw new IllegalArgumentException();
        MyDate medDate = new MyDate();
        medDate.mySetMonth(Integer.parseInt(dateData[1]));
        medDate.mySetYear(Integer.parseInt(dateData[2]));
        medDate.setDate(Integer.parseInt(dateData[0]));
        crMed.setExpDate(medDate);
        for(char c:medData[1].toCharArray()) if(!Character.isDigit(c)) throw new IllegalArgumentException();
        crMed.setPrice(Float.parseFloat(medData[2]));
        crMed.setName(medData[0]);
        crMed.setId(medData[1]);
        crMed.setQty(Integer.parseInt(medData[4]));
        crMed.setUnit(Integer.parseInt(medData[5]));
        idss.add(medData[1]);
        return crMed;
    }
    public PharmacyFrame() {
        initComponents();
        this.setResizable(false);


        this.setVisible(false);
        fileName = "medecines.txt";
        Scanner scanner = null;
        try{
            medecinesFile = new File(fileName);
            if(!medecinesFile.exists()) throw new FileNotFoundException();
            if((!medecinesFile.canRead()) || (!medecinesFile.canWrite())) throw new SecurityException();
            scanner = new Scanner(medecinesFile);
            if(scanner.hasNext()){
                // fillAList(scanner);
                this.goToInsert();
                scanner.close();


            }
            else this.firstGoToInsert();
        }
        catch(SecurityException SE){
            int i = JOptionPane.showConfirmDialog(null,"File had a problem while opening\nDo you want to create new file?");

            if(i == 0){
                try {
                    while(true){
                        fileName = "medecines"+Integer.toString(no)+".txt";
                        boolean b = medecinesFile.createNewFile();
                        if(b) break;
                        no++;
                    }
                } catch (IOException ex) {

                }
            }
            else System.exit(0);


        }
        catch(FileNotFoundException FE){
            int i = JOptionPane.showConfirmDialog(null,"There's no file with the same name\nDo you want to create new file?");
            if(i == 0){
                try {
                    while(true){
                        fileName = "medecines"+Integer.toString(no)+".txt";
                        boolean b = medecinesFile.createNewFile();
                        if(b) break;
                        no++;
                    }
                } catch (IOException ex) {

                }
            }
            else System.exit(0);


        }
        catch(RuntimeException re){
            if(JOptionPane.showConfirmDialog(null, "There's a problem in the data in the file\n"
                    + "Do you want to destroy it?")==0){
                try {
                    scanner.close();
                    FileWriter fr = new FileWriter(medecinesFile);
                    fr.close();
                    this.firstGoToInsert();
                } catch (IOException ex) {
                    Logger.getLogger(PharmacyFrame.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
            else if(JOptionPane.showConfirmDialog(null, "Do you want to create a new file?"
            )==0){
                try {
                    while(true){
                        fileName = "medecines"+Integer.toString(no)+".txt";
                        boolean b = medecinesFile.createNewFile();
                        if(b) break;
                        no++;
                    }
                } catch (IOException ex) {

                }
            }
            else {
                scanner.close();
                System.exit(0);
            }

        }
        catch(Exception E){
            //int i = JOptionPane.showConfirmDialog(null,"An error in the file ocurred\nDo you want to create new file?");
            if(JOptionPane.showConfirmDialog(null,"An error in the file ocurred\nDo you want to create new file?") == 0){
                try {
                    while(true){
                        fileName = "medecines"+Integer.toString(no)+".txt";
                        boolean b = medecinesFile.createNewFile();
                        if(b) break;
                        no++;
                    }
                } catch (IOException ex) {

                }
            }
            else System.exit(0);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        modeGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        insertRaButton = new javax.swing.JRadioButton();
        searchRaButton = new javax.swing.JRadioButton();
        UDRaButton = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        idField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        priceField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        qtyField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        unitField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        dayField = new javax.swing.JTextField();
        monthField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        yearField = new javax.swing.JTextField();
        idAvaLabel = new javax.swing.JLabel();
        idInvalidLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Pharmacy");

        modeGroup.add(insertRaButton);
        insertRaButton.setText("Insert");
        insertRaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertRaButtonActionPerformed(evt);
            }
        });

        modeGroup.add(searchRaButton);
        searchRaButton.setText("Search");
        searchRaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchRaButtonActionPerformed(evt);
            }
        });

        modeGroup.add(UDRaButton);
        UDRaButton.setText("Update or Delete");
        UDRaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UDRaButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Mode");

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Name");

        jLabel4.setText("Id");

        idField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idFieldActionPerformed(evt);
            }
        });
        idField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                idFieldKeyReleased(evt);
            }
        });

        jLabel5.setText("Price");

        jLabel6.setText("Quantity");

        jLabel7.setText("Unit");

        jLabel8.setText("Exp Date");

        jLabel9.setText("Day");

        dayField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dayFieldActionPerformed(evt);
            }
        });

        monthField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthFieldActionPerformed(evt);
            }
        });

        jLabel10.setText("Month");

        jLabel11.setText("Year");

        yearField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearFieldActionPerformed(evt);
            }
        });

        idAvaLabel.setForeground(new java.awt.Color(255, 0, 0));
        idAvaLabel.setText("Id is alredy available");

        idInvalidLabel.setForeground(new java.awt.Color(255, 0, 0));
        idInvalidLabel.setText("Id is invalid");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(158, 158, 158)
                                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel2)
                                                                .addGap(35, 35, 35)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(16, 16, 16)
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                        .addComponent(jLabel7)
                                                                                                        .addComponent(jLabel6))
                                                                                                .addGap(70, 70, 70)
                                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                                        .addComponent(qtyField)
                                                                                                        .addComponent(unitField, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)))
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                        .addComponent(jLabel3)
                                                                                                        .addComponent(jLabel4)
                                                                                                        .addComponent(jLabel5))
                                                                                                .addGap(74, 74, 74)
                                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                                        .addComponent(nameField, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                                                                                        .addComponent(idField)
                                                                                                        .addComponent(priceField)
                                                                                                        .addComponent(idInvalidLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(insertRaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(10, 10, 10)
                                                                                .addComponent(searchRaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(UDRaButton)))))
                                                .addGap(0, 19, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(36, 36, 36)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(idAvaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel8)
                                                                .addGap(53, 53, 53)
                                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(dayField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(8, 8, 8)
                                                .addComponent(monthField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel11)
                                                .addGap(18, 18, 18)
                                                .addComponent(yearField)))
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(insertRaButton)
                                        .addComponent(searchRaButton)
                                        .addComponent(jLabel2)
                                        .addComponent(UDRaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(idField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(idAvaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(idInvalidLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel9)
                                        .addComponent(dayField, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(monthField, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel11)
                                        .addComponent(yearField, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(qtyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(unitField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(saveButton)
                                        .addComponent(searchButton))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(updateButton)
                                        .addComponent(deleteButton))
                                .addGap(45, 45, 45))
        );

        pack();
    }// </editor-fold>

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {

        try{
            Medecine temp = new Medecine();
            if(this.nameField.getText().trim().length()==0) throw new InputMismatchException();
            temp.setId(tempId);
            MyDate tempDate = new MyDate();
            tempDate.setDate(Integer.parseInt(this.dayField.getText().trim()));
            tempDate.mySetMonth(Integer.parseInt(this.monthField.getText().trim()));
            tempDate.mySetYear(Integer.parseInt(this.yearField.getText().trim()));
            temp.setExpDate(tempDate);
            temp.setPrice(Float.parseFloat(this.priceField.getText().trim()));
            temp.setName(this.nameField.getText().trim());
            temp.setQty(Integer.parseInt(this.qtyField.getText().trim()));
            temp.setUnit(Integer.parseInt(this.unitField.getText().trim()));

            for(int i=0; i<meds.size();i++)
                if(meds.get(i).getId().equals(tempId)){
                    meds.remove(i);
                    meds.add(i, temp);
                }
        }
        catch(RuntimeException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
            return;
        }
        FileWriter wr= null;
        try {
            wr = new FileWriter(this.medecinesFile);
        } catch (IOException ex) {
            Logger.getLogger(PharmacyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(Medecine m : meds)try {
            wr.append(m.toString()+"\n");
        } catch (IOException ex) {
            Logger.getLogger(PharmacyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            wr.close();
        } catch (IOException ex) {
            Logger.getLogger(PharmacyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.goToInsert();
    }

    private void dayFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void UDRaButtonActionPerformed(java.awt.event.ActionEvent evt) {
        idss.removeAll(idss);
        meds.removeAll(meds);
        Scanner s= null;
        try{
            s= new Scanner(this.medecinesFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PharmacyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.fillAList(s);
        s.close();
        this.saveButton.setEnabled(false);
        this.searchButton.setEnabled(false);
        this.deleteButton.setEnabled(true);
        this.updateButton.setEnabled(true);
        this.idAvaLabel.setVisible(false);
        this.idInvalidLabel.setVisible(false);

        this.dayField.setEditable(true);
        this.monthField.setEditable(true);
        this.yearField.setEditable(true);
        this.idField.setEditable(false);
        this.nameField.setEditable(true);
        this.priceField.setEditable(true);
        this.unitField.setEditable(true);
        this.qtyField.setEditable(true);


    }

    private void monthFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void yearFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void idFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void idFieldKeyReleased(java.awt.event.KeyEvent evt) {
        for(char c: idField.getText().trim().toCharArray()){
            if(!Character.isDigit(c)){
                this.idInvalidLabel.setVisible(true);
                this.saveButton.setEnabled(false);
                this.idAvaLabel.setVisible(false);
                return;
            }

        }
        if(this.idss.contains(this.idField.getText().trim())){
            this.idInvalidLabel.setVisible(false);
            this.saveButton.setEnabled(false);
            this.idAvaLabel.setVisible(true);
            return;
        }
        else{
            this.idInvalidLabel.setVisible(false);
            this.saveButton.setEnabled(true);
            this.idAvaLabel.setVisible(false);
            return;
        }
    }

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        FileWriter wr = null;
        try{
            Medecine temp = new Medecine();
            if(this.nameField.getText().trim().length() ==0 ) throw new InputMismatchException();

            MyDate tempDate = new MyDate();
            tempDate.setDate(Integer.parseInt(this.dayField.getText().trim()));
            tempDate.mySetMonth(Integer.parseInt(this.monthField.getText().trim()));
            tempDate.mySetYear(Integer.parseInt(this.yearField.getText().trim()));
            temp.setExpDate(tempDate);

            temp.setName(this.nameField.getText().trim());
            temp.setId(this.idField.getText().trim());
            temp.setPrice(Float.parseFloat(this.priceField.getText().trim()));
            temp.setUnit(Integer.parseInt(this.unitField.getText().trim()));
            temp.setQty(Integer.parseInt(this.qtyField.getText().trim()));

            meds.add(temp);

            wr = new FileWriter(this.medecinesFile);
            for(Medecine m : meds)wr.append(m.toString()+"\n");

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        finally{
            try {
                if(wr != null)wr.close();
                Scanner s = new Scanner(this.medecinesFile);
                if(!s.hasNextLine()) this.firstGoToInsert();
                else this.goToInsert();
                s.close();
            } catch (Exception ex) {
                Logger.getLogger(PharmacyFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @SuppressWarnings("null")
    private void searchRaButtonActionPerformed(java.awt.event.ActionEvent evt) {
        idss.removeAll(idss);
        meds.removeAll(meds);
        Scanner s = null;
        try {
            s = new Scanner(this.medecinesFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PharmacyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.fillAList(s);
        s.close();
        this.saveButton.setEnabled(false);
        this.searchButton.setEnabled(true);
        this.deleteButton.setEnabled(false);
        this.updateButton.setEnabled(false);

        this.idAvaLabel.setVisible(false);
        this.idInvalidLabel.setVisible(false);

        this.dayField.setEditable(false);
        this.monthField.setEditable(false);
        this.yearField.setEditable(false);
        this.idField.setEditable(true);
        this.nameField.setEditable(false);
        this.priceField.setEditable(false);
        this.unitField.setEditable(false);
        this.qtyField.setEditable(false);

        this.dayField.setText("");
        this.monthField.setText("");
        this.yearField.setText("");
        this.idField.setText("");
        this.nameField.setText("");
        this.priceField.setText("");
        this.unitField.setText("");
        this.qtyField.setText("");

        this.UDRaButton.setEnabled(false);
        this.searchRaButton.setEnabled(true);

        this.searchRaButton.setSelected(true);

    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        for(Medecine m : meds) if(m.getId().equals(this.idField.getText().trim()))
        {

            this.dayField.setText(Integer.toString(m.getExpDate().getDate()));
            this.monthField.setText(Integer.toString(m.getExpDate().myGetMonth()));
            this.yearField.setText(Integer.toString(m.getExpDate().myGetYear()));
            //this.idField.setText("");
            this.nameField.setText(m.getName());
            this.priceField.setText(Float.toString(m.getPrice()));
            this.unitField.setText(Integer.toString(m.getUnit()));
            this.qtyField.setText(Integer.toString(m.getQty()));

            JOptionPane.showMessageDialog(null, "Found");
            tempId = m.getId();

            this.UDRaButton.setEnabled(true);

            return;
        }
        this.UDRaButton.setEnabled(false);
        JOptionPane.showMessageDialog(null, "Id not found or invalid");
        this.dayField.setText("");
        this.monthField.setText("");
        this.yearField.setText("");
        //this.idField.setText("");
        this.nameField.setText("");
        this.priceField.setText("");
        this.unitField.setText("");
        this.qtyField.setText("");


    }

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {
        for(Medecine m: meds) if(m.getId().equals(tempId)) meds.remove(m);

        Scanner s= null;

        try {
            FileWriter wr = new FileWriter(this.medecinesFile);
            for(Medecine m : meds)wr.append(m.toString()+"\n");
            wr.close();
            s = new Scanner(medecinesFile);
        } catch (Exception ex) {
            Logger.getLogger(PharmacyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!s.hasNextLine()) {this.firstGoToInsert();
            s.close();
            return;
        }
        this.goToInsert();

    }

    private void insertRaButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Scanner s = null;
        try {
            s= new Scanner(this.medecinesFile);
            if(!s.hasNextLine()) this.firstGoToInsert();
            else this.goToInsert();
            s.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PharmacyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PharmacyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PharmacyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PharmacyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PharmacyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PharmacyFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JRadioButton UDRaButton;
    private javax.swing.JTextField dayField;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel idAvaLabel;
    private javax.swing.JTextField idField;
    private javax.swing.JLabel idInvalidLabel;
    private javax.swing.JRadioButton insertRaButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.ButtonGroup modeGroup;
    private javax.swing.JTextField monthField;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField priceField;
    private javax.swing.JTextField qtyField;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JRadioButton searchRaButton;
    private javax.swing.JTextField unitField;
    private javax.swing.JButton updateButton;
    private javax.swing.JTextField yearField;
    // End of variables declaration
}
