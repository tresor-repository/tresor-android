package com.tresor.common.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by kris on 9/10/17. Tokopedia
 */

public class NoTitleDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(STYLE_NO_TITLE);
        return dialog;
    }
}
