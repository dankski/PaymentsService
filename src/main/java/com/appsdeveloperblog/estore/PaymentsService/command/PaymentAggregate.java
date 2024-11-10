package com.appsdeveloperblog.estore.PaymentsService.command;

import org.axonframework.spring.stereotype.Aggregate;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;

import com.appsdeveloperblog.estore.core.commands.ProcessPaymentCommand;
import com.appsdeveloperblog.estore.core.events.PaymentProcessedEvent;

@Aggregate
public class PaymentAggregate {

  @AggregateIdentifier
  private String paymentId;
  private String orderId;

  public PaymentAggregate() {}


  @CommandHandler
  public PaymentAggregate(ProcessPaymentCommand processPaymentCommand) {

    	if(processPaymentCommand.getPaymentDetails() == null) {
    		throw new IllegalArgumentException("Missing payment details");
    	}
    	
    	if(processPaymentCommand.getOrderId() == null) {
    		throw new IllegalArgumentException("Missing orderId");
    	}
    	
    	if(processPaymentCommand.getPaymentId() == null) {
    		throw new IllegalArgumentException("Missing paymentId");
    	}
	
      AggregateLifecycle.apply(PaymentProcessedEvent.builder()
      .orderId(processPaymentCommand.getOrderId())
      .paymentId(processPaymentCommand.getPaymentId())
      .build());

  }

  @EventSourcingHandler
  protected void on(PaymentProcessedEvent paymentProcessedEvent) {
    this.orderId = paymentProcessedEvent.getOrderId();
    this.paymentId = paymentProcessedEvent.getPaymentId();
  }

}
