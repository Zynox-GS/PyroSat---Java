package br.com.pysat.interfaces;

import br.com.pysat.entities.Alerta;

public interface Notificavel {
    void receberAlerta(Alerta alerta);
    String getCanaisNotificacao();
}
