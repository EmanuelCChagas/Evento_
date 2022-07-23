package com.howiv.evento_.utils;

import android.content.Context;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ConfirmDialog {

    public static void confirmDelete(Context context, Runnable callback) {
        new MaterialAlertDialogBuilder(context)
                .setTitle("Excluir")
                .setMessage("Tem certeza que deseja excluir os itens selecionados?")
                .setPositiveButton("Confirmar", (dialog, which) -> callback.run())
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel())
                .show();
    }

}
