import React from 'react';
import './single-record-view.scss';
import { connect } from 'react-redux';
import { RouteComponentProps, Link } from 'react-router-dom';
import { IActivity } from 'app/shared/model/activity.model';
import { OrganizationDetails } from '../shared/components/organization-details';
import { LocationsDetails } from '../shared/components/location/locations-details';
import { ServicesDetails } from '../shared/components/service/services-details';
import { ContactsDetails } from '../shared/components/contact/contacts-details';
import { Col, Row, Jumbotron } from 'reactstrap';
import { Translate } from 'react-jhipster';

export interface ISingleRecordViewProp extends StateProps, DispatchProps, RouteComponentProps<{}> {
  activity: IActivity;
  isBaseRecord: boolean;
}

export interface ISingleRecordViewState {
  activeTab: string;
}

export class Details extends React.Component<ISingleRecordViewProp, ISingleRecordViewState> {
  state: ISingleRecordViewState = {
    activeTab: '1'
  };

  toggle = tab => {
    if (this.state.activeTab !== tab) {
      this.setState({
        activeTab: tab
      });
    }
  };

  render() {
    const { activity } = this.props;
    const firstMatch = activity.organizationMatches.length !== 0 ? activity.organizationMatches[0] : null;

    const sideSection = firstMatch ? (
      <Col sm="3">
        <Jumbotron className="jumbotron">
          <div className="jumbotron-header">
            <h4 className="jumbotron-header-title">
              <Translate contentKey="singleRecordView.details.compare.title" />
            </h4>
            <p className="jumbotron-header-text">
              {activity.organizationMatches.length}
              {activity.organizationMatches.length === 1 ? (
                <Translate contentKey="singleRecordView.details.compare.singleHeader" />
              ) : (
                <Translate contentKey="singleRecordView.details.compare.multiHeader" />
              )}
            </p>
          </div>
          <div className="jumbotron-content">
            <p>
              <Translate contentKey="singleRecordView.details.compare.content1" />
            </p>
            <p>
              <Translate contentKey="singleRecordView.details.compare.content2" />
            </p>
            <p className="review-title">
              <Translate contentKey="singleRecordView.details.compare.review" />
            </p>
            <div>
              <Link to={`/multi-record-view/${firstMatch.organizationRecordId}`}>
                {activity.organizationMatches.length === 1
                  ? `${firstMatch.partnerVersionName}`
                  : `${firstMatch.partnerVersionName} +${activity.organizationMatches.length - 1}`}
              </Link>
            </div>
          </div>
        </Jumbotron>
      </Col>
    ) : null;

    const columnSize = 6;
    return (
      <div>
        <OrganizationDetails {...this.props} sideSection={sideSection} columnSize={columnSize} />
        <LocationsDetails {...this.props} columnSize={columnSize} />
        <ServicesDetails {...this.props} columnSize={columnSize} />
        <ContactsDetails {...this.props} contacts={this.props.activity.record.contacts} columnSize={columnSize} />
      </div>
    );
  }
}

const mapStateToProps = () => ({});

const mapDispatchToProps = {};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Details);
