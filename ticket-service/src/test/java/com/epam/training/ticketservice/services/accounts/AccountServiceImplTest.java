package com.epam.training.ticketservice.services.accounts;

import com.epam.training.ticketservice.services.movies.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    private final String USERNAME1 = "admin";
    private final String USERNAME2 = "admin2";
    private final String PASSWORD1 = "admin";
    private final boolean PRIVILEGED1 = true;


    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private final AccountServiceImpl underTest = new AccountServiceImpl(accountRepository);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addUserShouldAddItToTheAccounts(){
        //Given
        Account ACCOUNT1 = new Account(USERNAME1, PASSWORD1, PRIVILEGED1);
        when(accountRepository.save(ACCOUNT1)).thenReturn(ACCOUNT1);

        //When
        underTest.addUser(ACCOUNT1);

        //Then
        verify(accountRepository).save(ACCOUNT1);
    }

    @Test
    public void findAccountByUsernameShouldReturnNullIfThereAreNoMatchingAccounts(){
        //Given
        Account ACCOUNT1 = new Account(USERNAME1, PASSWORD1, PRIVILEGED1);
        when(accountRepository.findAccountByUsername(USERNAME1)).thenReturn(ACCOUNT1);

        //Then
        assertNull(underTest.findAccountByUsername(USERNAME2));
    }

    @Test
    public void findAccountByUserByeUsernameShouldReturnTheAccountIfNameIsValid(){
        //Given
        Account ACCOUNT1 = new Account(USERNAME1, PASSWORD1, PRIVILEGED1);
        when(accountRepository.findAccountByUsername(USERNAME1)).thenReturn(ACCOUNT1);

        //When
        underTest.findAccountByUsername(USERNAME1);

        //Then
        verify(accountRepository).findAccountByUsername(USERNAME1);
    }

    @Test
    public void signInShouldReturnNullIfPasswordIsIncorrect(){
        //Given
        Account ACCOUNT1 = new Account(USERNAME1, PASSWORD1, PRIVILEGED1);
        when(accountRepository.save(ACCOUNT1)).thenReturn(ACCOUNT1);
        when(accountRepository.findAccountByUsername(USERNAME1)).thenReturn(ACCOUNT1);
        underTest.addUser(ACCOUNT1);

        //Then
        assertNull(underTest.signIn(USERNAME1, "wrong_password"));
    }

    @Test
    public void signInShouldReturnTheAccountIfThePasswordIsCorrect(){
        //Given
        Account ACCOUNT1 = new Account(USERNAME1, PASSWORD1, PRIVILEGED1);
        when(accountRepository.save(ACCOUNT1)).thenReturn(ACCOUNT1);
        when(accountRepository.findAccountByUsername(USERNAME1)).thenReturn(ACCOUNT1);
        underTest.addUser(ACCOUNT1);

        //Then
        assertEquals(ACCOUNT1, underTest.signIn(USERNAME1, PASSWORD1));
    }

}