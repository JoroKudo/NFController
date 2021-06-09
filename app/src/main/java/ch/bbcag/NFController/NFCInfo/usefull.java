/*
for (int i = 0; i <= ndefMessages.length; i++) {
        for (int j = 0; j <= ndefMessages[i].getRecords().length; j++) {
        NdefRecord record = ndefMessages[i].getRecords()[j];
        byte[] payload = record.getPayload();
        String text = new String(payload);
        byte[] typeAsType = record.getType();

        BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();

        if (text.isEmpty()) {
        listTitle.setText("Empty Tag");
        } else if (text == "bluetooth") {
        bAdapter.disable();


        } else {


        listTitle.setText(text);
        }
        }
        }
 */