package com.university.brailleflip.thread;

public class ThreadManager {
    private ConnectedMsgThread serviceConnected;
    private ConnectedMsgThread clientConnected;
    private static ThreadManager threadManager;
    private CoordinateData coordinateData;
    public static boolean connected=false;
    public interface CoordinateData{
        void data(String str);
    }

    public CoordinateData getCoordinateData() {
        return coordinateData;
    }

    public void setCoordinateData(CoordinateData coordinateData) {
        this.coordinateData = coordinateData;
    }

    private ThreadManager() {
    }

    public static ThreadManager getInstance(){
        if (threadManager==null) {
            threadManager = new ThreadManager();
        }
        return threadManager;
    }

    public ConnectedMsgThread getServiceConnected() {
        return serviceConnected;
    }

    public void setServiceConnected(ConnectedMsgThread serviceConnected) {
        this.serviceConnected = serviceConnected;
    }

    public ConnectedMsgThread getClientConnected() {
        return clientConnected;
    }

    public void setClientConnected(ConnectedMsgThread clientConnected) {
        this.clientConnected = clientConnected;
    }
}
