package com.assl.volleyapp;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SignInFragment extends Fragment implements TextWatcher, View.OnFocusChangeListener {

    private MainActivityViewModel mainActivityViewModel;
    private TextInputLayout email, password;
    private Button login, signUp;
    private ConstraintLayout parentLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        addFocusChangeListener();
        populateViews();
        addTextWatcher();
        addClickListener();
    }

    private void addClickListener() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(requireContext(), "Logging in...", Toast.LENGTH_SHORT).show();
                if(!Objects.requireNonNull(email.getEditText()).getText().toString().trim().equals("") &&
                        !Objects.requireNonNull(password.getEditText()).getText().toString().trim().equals("")){
                    SignInUserDetails signInUserDetails = new SignInUserDetails();
                    signInUserDetails.setProfileCode("PFR240720200007");
                    signInUserDetails.setSellerCode("SLR240720200007");
                    signInUserDetails.setUsername(email.getEditText().toString().trim());
                    signInUserDetails.setPassword(password.getEditText().toString().trim());
                    MutableLiveData<String> responseLiveData = mainActivityViewModel.signInUser(signInUserDetails, requireContext());
                    responseLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
//                            Log.e("Login","response: "+s);
                            if(s.charAt(0)=='s')
                                Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(requireContext(), "Please fill username/password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityViewModel.setEmail("");
                mainActivityViewModel.setPassword("");
                Navigation.findNavController(view).navigate(R.id.signUpFragment);
            }
        });
    }

    private void addFocusChangeListener() {
        parentLayout.setOnFocusChangeListener(this);
    }

    private void addTextWatcher() {
        Objects.requireNonNull(email.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(password.getEditText()).addTextChangedListener(this);
    }

    private void populateViews() {
        if(!mainActivityViewModel.getEmail().equals(""))
            Objects.requireNonNull(email.getEditText()).setText(mainActivityViewModel.getEmail());

        if(!mainActivityViewModel.getPassword().equals(""))
            Objects.requireNonNull(password.getEditText()).setText(mainActivityViewModel.getPassword());
    }

    private void initViews(View view) {
        parentLayout = view.findViewById(R.id.sign_in_parent_view);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        login = view.findViewById(R.id.sign_in_button);
        signUp = view.findViewById(R.id.sign_up_button);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String currentString = charSequence.toString();
        if(email.hasFocus())
            mainActivityViewModel.setEmail(currentString);
        else if(password.hasFocus())
            mainActivityViewModel.setPassword(currentString);
    }

    @Override
    public void afterTextChanged(Editable editable) {}

    @Override
    public void onFocusChange(View view, boolean b) {
        if(b && !(view instanceof EditText))
            hideKeyboard(view);
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm!=null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}