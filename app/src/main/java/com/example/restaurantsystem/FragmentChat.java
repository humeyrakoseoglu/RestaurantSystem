package com.example.restaurantsystem;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentChat extends Fragment {
    EditText userInput;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;
    List<ResponseMessage> responseMessageList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat,container,false);

        userInput = view.findViewById(R.id.userInput);
        recyclerView = view.findViewById(R.id.conversation);
        responseMessageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(responseMessageList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(messageAdapter);
        ResponseMessage responseMessage1 = new ResponseMessage("Hello, welcome to Taurus Steakhouse, how can I help you?", false);
        responseMessageList.add(responseMessage1);
        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND) {

                    ResponseMessage responseMessage = new ResponseMessage(userInput.getText().toString(), true);
                    responseMessageList.add(responseMessage);
                    ResponseMessage responseMessage3;
                    String userinputtext=userInput.getText().toString();

                    if(userinputtext.equals("hi")||userinputtext.equals("hello")){
                         responseMessage3 = new ResponseMessage("Hello, how can I help you?", false);
                    responseMessageList.add(responseMessage3);}

                    else if(userinputtext.equals("Where is my order?")||userinputtext.equals("order")) {
                        responseMessage3 = new ResponseMessage("Your order will be delivered within 30 minutes.", false);
                        responseMessageList.add(responseMessage3);
                    }
                    else if(userinputtext.equals("Where can I book?")||userinputtext.equals("reservation")||userinputtext.equals("How can I make a reservation?")) {
                        responseMessage3 = new ResponseMessage("You can make a reservation by pressing the make a reservation button on the homepage.", false);
                        responseMessageList.add(responseMessage3);
                    }
                    else if(userinputtext.equals("Where is your restaurant?")||userinputtext.equals("What is the address of your restaurant?")||userinputtext.equals("address")) {
                        responseMessage3 = new ResponseMessage("903 N La Cienega Blvd, Los Angeles, CA 90069, United States.", false);
                        responseMessageList.add(responseMessage3);
                    }
                    else if(userinputtext.equals("What is your restaurant's phone number?")||userinputtext.equals("phone number")) {
                        responseMessage3 = new ResponseMessage("+13106575711", false);
                        responseMessageList.add(responseMessage3);
                    }
                    else if(userinputtext.equals("bye")||userinputtext.equals("thank you")||userinputtext.equals("thanks")) {
                        responseMessage3 = new ResponseMessage("Thank you for choosing us. Have a nice day!", false);
                        responseMessageList.add(responseMessage3);
                    }
                    else{
                        responseMessage3 = new ResponseMessage("Sorry. I couldn't understand your question.", false);
                        responseMessageList.add(responseMessage3);
                    }

                    messageAdapter.notifyDataSetChanged();
                    if (!isLastVisible())
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                }
                return false;
            }
        });
        return view;
    }

    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = recyclerView.getAdapter().getItemCount();
        return (pos >= numItems);
    }
}