# LendingApi

An application that simulates a Lending/Repayment API.
The Developed API has the following requirements met:
1. Receive a Lending request from a user (Loans are tied to a subscriber
   MSISDN)
2. Receive repayment requests from the user should they top up their loans and update
   the relevant tables
3. Add logic to sweep/clear old defaulted loans (The decision to clear the loans is
    configurable as that may vary from market to market. ie : Should the loan be cleared
   after a loan age of 6 months?)
4. As you’re designing the API make decisions such as whether a user can have one or
   multiple loans. Your database should cater for that.
5. Once a subscriber takes a loan or makes a repayment they are notified by
   SMS of the amount lent if it's a lending operation or the amount recovered if full or
   partial.