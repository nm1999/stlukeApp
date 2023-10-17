//package com.jcmtechug.stlukeapp.Adapter;
//
//import android.widget.Filter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class NameFilter extends Filter {
//
//    private List<String> originalData;
//    private List<String> filteredData;
//
//    public NameFilter(List<String> originalData) {
//        this.originalData = originalData;
//        this.filteredData = new ArrayList<>(originalData);
//    }
//
//    @Override
//    protected FilterResults performFiltering(CharSequence constraint) {
//        FilterResults results = new FilterResults();
//
//        if (constraint == null || constraint.length() == 0) {
//            results.values = originalData;
//            results.count = originalData.size();
//        } else {
//            List<String> filteredList = new ArrayList<>();
//            for (String item : originalData) {
//                if (item.contains(constraint)) {
//                    filteredList.add(item);
//                }
//            }
//
//            results.values = filteredList;
//            results.count = filteredList.size();
//        }
//
//        return results;
//    }
//
//    @Override
//    protected void publishResults(CharSequence constraint, FilterResults results) {
//        filteredData = (List<String>) results.values;
////        notifyDataSetChanged();
//    }
//
////    @Override
////    public int getCount() {
////        return filteredData.size();
////    }
////
////    public String getItem(int position) {
////        return filteredData.get(position);
////    }
////
////    @Override
////    public long getItemId(int position) {
////        return position;
////    }
//}