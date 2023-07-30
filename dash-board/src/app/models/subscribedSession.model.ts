export interface SubscribedSession {
    id: number,
    paymentAt: string,
    expiredAt: string,
    state: boolean,

    activity: string,
    activityDescription: string,
    room: string,

    classTime: string,

    monday: boolean,
    tuesday: boolean,
    wednesday: boolean,
    thursday: boolean,
    friday: boolean,
    saturday: boolean,
    sunday: boolean
}