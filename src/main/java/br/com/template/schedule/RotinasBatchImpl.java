package br.com.template.schedule;

import br.com.template.schedule.interfaces.RotinasBatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@RequiredArgsConstructor
@Log
public class RotinasBatchImpl implements RotinasBatch {

    //private final ObjetoService service;

    /*
    private final long SEGUNDO = 1000;
    private final long MINUTO = SEGUNDO * 60;
    private final long HORA = MINUTO * 60;
    @Scheduled(fixedRate = HORA * 3)
    */


    /*
    * ┌───────────── second (0-59)  ┌─────────────
    * │  ┌──────────── minute (0 - 59)  ┌─────────────
    * │  │ ┌─────────── hour (0 - 23)   ┌─────────────
    * │  │ │ ┌───────── day of the month (1 - 31) ┌─────────────│ │ │ │
    * │  │ │ │ ┌──────── month (1 - 12) (or JAN-DEC) ┌─────────────
    * │  │ │ │ │ ┌──── day of the week (0 - 7) or ( MON-SUN -- 0 or 7 is Sunday) ┌───────────── │ │ │
    * │  │ │ │ │ |
    * 0  0 7 * * *│
    * */

    /**
     * Meses: Todos
     * Dias: 10 até 20
     * Dias da Semana: Seg a Sex
     * Inicio: 8:00
     * Termino: 20:00
     * Obs: de 2 em 2 hotas
     */
    @Override
    @Scheduled(cron = "0 0 8,10,12,14,16,18,20 10-20 * 0-4")
    public void metodo1() {

        //service.metodo();
    }

    /**
     * Meses: Todos
     * Dias: 1 até 9 e 21 até 31
     * Dias da Semana: Seg a Sex
     * Horário: 12:00
     * Obs: Rodará uma única vez por dia
     */
    @Override
    @Scheduled(cron = "0 0 12 01-9,21-31 * 0-4")
    public void metodo2() {
        //service.metodo();
    }
}
