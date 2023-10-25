/*public class NextTicketId{

    public static Long getNetxTicketId(Counter counter) {
        
        // services_array = select id from service order by service time asc
        // for each service
        // queue =
        // select id (ticket)
        // from join counter e service e ticket transazione
        // where join condition and counter = counter.getid() and service = services_array

        // ArrayList<Long> queues_list;
        // {queues_list.add(queue)}

        //stop for
        // now we have in the queues_list the queues ordered by service time 

        //this.getNextId(queues_list);

    }

    private String getNextId(ArrayList<String[]> list){
        String[] selectedQueue = {}; // queue where to chose the next id
        int len = 0; // max queue length
        
        if(list.size() == 0){
        return "Queue Empty"; 
        }
        
        // search for the maximum queue length
        for(String[] queue : list){
            if (queue.length > len){
                len = queue.length;
                selectedQueue = queue;
            }
        }
        
        return selectedQueue[0];
        
        
        
    }

}*/