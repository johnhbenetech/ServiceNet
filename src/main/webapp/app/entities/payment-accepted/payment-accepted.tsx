import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './payment-accepted.reducer';
import { IPaymentAccepted } from 'app/shared/model/payment-accepted.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPaymentAcceptedProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class PaymentAccepted extends React.Component<IPaymentAcceptedProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { paymentAcceptedList, match } = this.props;
    return (
      <div>
        <h2 id="payment-accepted-heading">
          <Translate contentKey="serviceNetApp.paymentAccepted.home.title">Payment Accepteds</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="serviceNetApp.paymentAccepted.home.createLabel">Create new Payment Accepted</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="serviceNetApp.paymentAccepted.payment">Payment</Translate>
                </th>
                <th>
                  <Translate contentKey="serviceNetApp.paymentAccepted.srvc">Srvc</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {paymentAcceptedList.map((paymentAccepted, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${paymentAccepted.id}`} color="link" size="sm">
                      {paymentAccepted.id}
                    </Button>
                  </td>
                  <td>{paymentAccepted.payment}</td>
                  <td>
                    {paymentAccepted.srvcName ? <Link to={`service/${paymentAccepted.srvcId}`}>{paymentAccepted.srvcName}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${paymentAccepted.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${paymentAccepted.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${paymentAccepted.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ paymentAccepted }: IRootState) => ({
  paymentAcceptedList: paymentAccepted.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PaymentAccepted);
